//Biblioteca para sensor DHT11
#include <DHT.h>
#define DHTPIN 2 
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

//Definición de pines para resto de sensores
int luz = A0;
int led = 3;
int lluvia = A1;

//Bibliotecas para ESP01 y mosquitto
#include <WiFiEsp.h>
#include <WiFiEspClient.h>
#include <PubSubClient.h>
#include "SoftwareSerial.h"

//Conexión a la red wifi: nombre de la red y contraseña
#define WIFI_AP "MiFibra-CD10"
#define WIFI_PASSWORD "DEqp2zGP"

//Nombre o IP del servidor mosquitto
char server[50] = "192.168.1.117";

//Inicializamos el objeto de cliente esp
WiFiEspClient espClient;

//Iniciamos el objeto subscriptor del cliente 
//con el objeto del cliente
PubSubClient client(espClient);

//Conexión serial para el esp con una comunicación
//serial, pines 9: rx y 10: tx
SoftwareSerial soft(9, 10);

//Contador para el envio de datos
unsigned long lastSend;

int status = WL_IDLE_STATUS;

void setup() {
    //Inicializamos la comunicación serial para el log
    Serial.begin(9600);
    //Iniciamos la conexión a la red WiFi
    InitWiFi();
    //Colocamos la referencia del servidor y el puerto
    client.setServer( server, 1883 );
    lastSend = 0;

    pinMode (luz, INPUT);
    pinMode (led, OUTPUT);
    pinMode (lluvia, INPUT);
    
    dht.begin();
}

void loop() {
    //Validamos si el modulo WiFi aun esta conectado a la red
    status = WiFi.status();
    if(status != WL_CONNECTED) {
        //Si falla la conexión, reconectamos el modulo
        reconnectWifi();
    }

    //Validamos si esta la conexión del servidor
    if(!client.connected() ) {
        //Si falla reintentamos la conexión
        reconnectClient();
    }

    //Creamos un contador para enviar la data cada 2 segundos
    if(millis() - lastSend > 2000 ) {
        sendDataTopic();
        lastSend = millis();
    }

    client.loop();
}

String sensores(){

  int humedad = dht.readHumidity();
  int temperatura = dht.readTemperature(false);

  if (isnan(humedad) || isnan(temperatura)) {
    Serial.println("Fallo en el sensor!");    
  }

  long int valorLuz = analogRead(luz);
  valorLuz = (valorLuz * 100)/1010;
  //int mapeo = map(valorLuz, 0, 100, 0, 255);
  //analogWrite(led, mapeo);

  int lluvias = analogRead(lluvia);
  bool llueve = false;
  if (lluvias <= 50){
    llueve = false;
  }else {
    llueve = true;
  }

  Serial.print("Temperatura: ");  Serial.print(temperatura);  Serial.println("ºC");   
  Serial.print("Humedad: ");  Serial.print(humedad);  Serial.println("%");
  Serial.print("\nLuz: ");  Serial.print(valorLuz);  Serial.println("%");
  Serial.print("Lluvias: ");  Serial.println(analogRead(lluvias)); 

String valores = "Temperatura:"; valores += temperatura; valores += ",Humedad:"; valores += humedad; valores += ",Luz:"; valores += valorLuz ; valores += ",Lluvias:"; valores += llueve;
  return valores;
}

void sendDataTopic()
{
    Serial.println("sendDataTopic");
    // Prepare a JSON payload string
    String payload = sensores();
    
    // Send payload
    char attributes[100];
    payload.toCharArray( attributes, 100 );
    client.publish( "EM001", attributes );
    Serial.println( attributes ); 
    digitalWrite (led, 1);
}

//Inicializamos la conexión a la red wifi
void InitWiFi()
{
    //Inicializamos el puerto serial
    soft.begin(9600);
    //Iniciamos la conexión wifi
    WiFi.init(&soft);
    //Verificamos si se pudo realizar la conexión al wifi
    //si obtenemos un error, lo mostramos por log y denememos el programa
    if (WiFi.status() == WL_NO_SHIELD) {
        Serial.println("El modulo WiFi no esta presente");
        while (true);
    }
    reconnectWifi();
}

void reconnectWifi() {
    Serial.println("Iniciar conección a la red WIFI");
    while(status != WL_CONNECTED) {
        Serial.print("Intentando conectarse a WPA SSID: ");
        Serial.println(WIFI_AP);
        //Conectar a red WPA/WPA2
        status = WiFi.begin(WIFI_AP, WIFI_PASSWORD);
        delay(500);
    }
    Serial.println("Conectado a la red WIFI");
}

void reconnectClient() {
    //Creamos un loop en donde intentamos hacer la conexión
    while(!client.connected()) {
        Serial.print("Conectando a: ");
        Serial.println(server);
        //Creamos una nueva cadena de conexión para el servidor
        //e intentamos realizar la conexión nueva
        //si requiere usuario y contraseña la enviamos connect(clientId, username, password)
        String clientId = "ESP8266Client-" + String(random(0xffff), HEX);
        if(client.connect(clientId.c_str())) {
            Serial.println("[DONE]");
        } else {
            Serial.print( "[FAILED] [ rc = " );
            Serial.print( client.state() );
            Serial.println( " : retrying in 5 seconds]" );
            delay( 5000 );
        }
    }
}
