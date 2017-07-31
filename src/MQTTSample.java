import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTSample {
    public static void main(String[] args) {
        String topic        = "weather";
        String content      = "Hello CloudMQTT";
        int qos             = 1;
        String broker       = "tcp://m10.cloudmqtt.com:10993";

        //MQTT client id to use for the device. "" will generate a client id automatically
        String clientId     = "";

        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            mqttClient.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage msg)
                        throws Exception {
                    System.out.println("Recived:" + topic);
                    System.out.println("Recived:" + new String(msg.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken arg0) {
                    System.out.println("Delivary complete");
                }

                public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                }
            });

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("soncute");
            connOpts.setPassword(new char[]{'1', '2', '3', '4', '5', '6', 'a', '@'});
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            System.out.println("Publish message: " + message);
            mqttClient.subscribe(topic, qos);
            mqttClient.publish(topic, message);
            //mqttClient.disconnect();
            //System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}