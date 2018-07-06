package interfaces.mappersXML;

import model.Payment;

import java.util.List;

public interface PaymentMapper {

    List<Payment> getPaymentsByIdSub(Integer subscriberId);
    void insertForSubsID(Payment payment);
    void updatePaymentForSubscriber(Payment payment);
    void deleteByID(long id);
    void deletePayment(Payment payment);

}
