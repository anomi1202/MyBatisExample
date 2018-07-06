import model.Payment;
import model.Subscriber;
import model.Tariff;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.Comparator;

public class Work {
    private SqlSession sqlSessionXML =  new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config1.xml")).openSession();
    private SqlSession sqlSessionAnnotation =  new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config3.xml")).openSession();
    private interfaces.mappersXML.SubscriberMapper subscriberMapperXML;
    private interfaces.mappersXML.TariffMapper tariffMapperXML;
    private interfaces.mappersXML.PaymentMapper paymentMapperXML;

    private interfaces.mappersAnnotation.SubscriberMapper subscriberMapperAnnotation;
    private interfaces.mappersAnnotation.TariffMapper tariffMapperAnnotation;
    private interfaces.mappersAnnotation.PaymentMapper paymentMapperAnnotation;

    public Work() throws IOException {
        //Читаем файл с настройками подключения и настройками MyBatis и создаем сессию и подгружаем маппер
        subscriberMapperXML = sqlSessionXML.getMapper(interfaces.mappersXML.SubscriberMapper.class);
        tariffMapperXML = sqlSessionXML.getMapper(interfaces.mappersXML.TariffMapper.class);
        paymentMapperXML = sqlSessionXML.getMapper(interfaces.mappersXML.PaymentMapper.class);

        subscriberMapperAnnotation = sqlSessionAnnotation.getMapper(interfaces.mappersAnnotation.SubscriberMapper.class);
        tariffMapperAnnotation = sqlSessionAnnotation.getMapper(interfaces.mappersAnnotation.TariffMapper.class);
        paymentMapperAnnotation = sqlSessionAnnotation.getMapper(interfaces.mappersAnnotation.PaymentMapper.class);
    }

    public static void main(String[] args) throws IOException {
        Work work = new Work();
        try {
            int id = 2;

            Subscriber subscriberById = work.subscriberMapperAnnotation.getSubscriberById(id);
            System.out.println(subscriberById);

            work.insertPaymentXML(subscriberById);
            subscriberById = work.subscriberMapperAnnotation.getSubscriberById(id);
            work.insertPaymentAnnotation(subscriberById);
            subscriberById = work.subscriberMapperAnnotation.getSubscriberById(id);
            System.out.println(subscriberById);

            work.deletePaymentByIDAnnotation(subscriberById);
            subscriberById = work.subscriberMapperAnnotation.getSubscriberById(id);
            System.out.println(subscriberById);

            work.deletePaymentAnnotation(subscriberById);
            System.out.println(work.subscriberMapperAnnotation.getSubscriberById(id));
        } finally {
            work.sqlSessionXML.rollback();
            work.sqlSessionAnnotation.rollback();

            work.sqlSessionXML.commit();
            work.sqlSessionXML.close();

            work.sqlSessionAnnotation.commit();
            work.sqlSessionAnnotation.close();
        }
    }

    Subscriber getLastSubscriberXML(){
        return subscriberMapperXML.getSubscribers().stream().max(Comparator.comparing(Subscriber::getId)).orElse(null);
    }
    Subscriber getLastSubscriberAnnotation(){
        return subscriberMapperAnnotation.getSubscribers().stream().max(Comparator.comparing(Subscriber::getId)).orElse(null);
    }
    Subscriber getSubscriberByIDXML(int id){
        return subscriberMapperXML.getSubscriberById(id);
    }
    Subscriber getSubscriberByIDAnnotation(int id){
        return subscriberMapperAnnotation.getSubscriberById(id);
    }

    Tariff getLastTariffXML(){
        return tariffMapperXML.getTariffs().stream().max(Comparator.comparing(Tariff::getId)).orElse(null);
    }
    Tariff getTariffByIDXML(int id){
        return tariffMapperAnnotation.getTariffById(id);
    }
    Tariff getLastTariffAnnotation(){
        return tariffMapperXML.getTariffs().stream().max(Comparator.comparing(Tariff::getId)).orElse(null);
    }
    Tariff getTariffByIDAnnotation(int id){
        return tariffMapperAnnotation.getTariffById(id);
    }

    void insertIntoSubscriberXML(){
        System.out.println("\r\ninsertXML");
        Subscriber newSubscriber = new Subscriber();
            newSubscriber.setName("newName");
            newSubscriber.setTariff(tariffMapperAnnotation.getTariffById(1));

            subscriberMapperXML.insertSubscriber(newSubscriber);
            sqlSessionXML.commit();
    }
    void insertIntoSubscriberAnnotation(){
        System.out.println("\r\ninserrtAnnotation2");
        Subscriber lastSubscriber = subscriberMapperAnnotation.getSubscribers().stream().max(Comparator.comparing(Subscriber::getId)).orElse(null);
        if (lastSubscriber != null){
            long nextSubscriberID = lastSubscriber.getId() + 1;
            Subscriber newSubscriber = new Subscriber();
                newSubscriber.setName("newName" + nextSubscriberID);
                newSubscriber.setTariff(tariffMapperAnnotation.getTariffById(1));

            subscriberMapperAnnotation.insertIntoSubscriber(newSubscriber);
            sqlSessionAnnotation.commit();
        }
    }

    void insertPaymentXML(Subscriber subscriber){
        System.out.println("\r\ninsertXML");
        long subscriberID = subscriber.getId();
        Payment payment = new Payment();
            payment.setSumma(5000);
            payment.setSubscriberID(subscriberID);
        paymentMapperXML.insertForSubsID(payment);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void insertPaymentAnnotation(Subscriber subscriber){
        System.out.println("insertAnnotation");
        Payment payment = new Payment();
            payment.setSumma(505);
            payment.setSubscriberID(subscriber.getId());

        paymentMapperAnnotation.insertForSubsID(payment);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }

    void insertTariffXML(){
        System.out.println("\r\ninsertXML");
        Tariff tariff = new Tariff();
            tariff.setDescr("newTariff");
        tariffMapperXML.insertTariff(tariff);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void insertTariffAnnotation(){
        Tariff lastTariff = tariffMapperAnnotation.getTariffs().stream().max(Comparator.comparing(Tariff::getId)).orElse(null);
        long lastTariffID = lastTariff!= null ? lastTariff.getId() : 1;

        Tariff newTariff = new Tariff();
            newTariff.setDescr("newTariff" + lastTariffID);
        tariffMapperAnnotation.insert(newTariff);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }

    void updateTariffXML(){
        Tariff tariff = getLastTariffXML();
            tariff.setDescr(tariff.getDescr() + tariff.getId() + "_update");
        tariffMapperXML.updateTariff(tariff);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void updateTariffAnnotation(){
        Tariff tariff = getLastTariffAnnotation();
        tariff.setDescr(tariff.getDescr() + tariff.getId() + "_update");
            tariffMapperAnnotation.updateTariff(tariff);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }

    void updatePaymentXML(Subscriber subscriber){
        Payment payment = subscriber.getPayments().stream().max(Comparator.comparing(Payment::getId)).orElse(null);
        if (payment == null) {
            insertPaymentXML(subscriber);
        } else {
            payment.setSumma(payment.getSumma() + 505);
        }
        paymentMapperXML.updatePaymentForSubscriber(payment);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void updatePaymentAnnotation(Subscriber subscriber){
        Payment payment = subscriber.getPayments().stream().max(Comparator.comparing(Payment::getId)).orElse(null);
        if (payment == null) {
            insertPaymentAnnotation(subscriber);
        } else {
            payment.setSumma(payment.getSumma() + 505);
        }
        paymentMapperAnnotation.updatePaymentForSubscriber(payment);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }

    void updateSubscriberXML(Subscriber subscriber){
        subscriberMapperXML.updateSubscriber(subscriber);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void updateSubscriberAnnotation(Subscriber subscriber){
        subscriberMapperAnnotation.updateSubscriber(subscriber);
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }

    void deletePaymentByIDXML(Subscriber subscriber){
        System.out.println("\r\ndeleteXML");
        Payment payment = subscriber.getPayments().stream().max(Comparator.comparing(Payment::getId)).orElse(null);
        if (payment != null) {
            paymentMapperXML.deleteByID(payment.getId());
        }
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void deletePaymentXML(Subscriber subscriber){
        System.out.println("deleteXML");
        Payment payment = subscriber.getPayments().stream().max(Comparator.comparing(Payment::getId)).orElse(null);
        if (payment != null) {
            paymentMapperXML.deletePayment(payment);
        }
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void deletePaymentByIDAnnotation(Subscriber subscriber){
        System.out.println("\r\ndeleteAnnotation");
        Payment payment = subscriber.getPayments().stream().max(Comparator.comparing(Payment::getId)).orElse(null);
        if (payment != null) {
            paymentMapperAnnotation.deleteByID(payment.getId());
        }
        sqlSessionXML.commit();
        sqlSessionAnnotation.commit();
    }
    void deletePaymentAnnotation(Subscriber subscriber) {
        System.out.println("deleteAnnotation");
        Payment payment = subscriber.getPayments().stream().max(Comparator.comparing(Payment::getId)).orElse(null);
        if (payment != null) {
            paymentMapperAnnotation.deletePayment(payment);
        }
        sqlSessionXML.commit();
        sqlSessionXML.rollback();
        sqlSessionAnnotation.commit();
        sqlSessionAnnotation.rollback();
    }
}
