package interfaces.mappersXML;

import model.Subscriber;

import java.util.List;

public interface SubscriberMapper {

    Subscriber getSubscriberById(int id);
    List<Subscriber> getSubscribers();
    void insertSubscriber(Subscriber subscriber);
    void updateSubscriber(Subscriber subscriber);
}
