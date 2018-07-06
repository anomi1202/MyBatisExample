package interfaces.mappersAnnotation;

import model.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PaymentMapper {

    @Select("select * from payments where id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "summa", column = "summa"),
            @Result(property = "subscriberID", column = "ref_subscriber")
    })
    List<Payment> getPaymentsById(Integer id);

    @Select("select * from payments where ref_subscriber = #{subscriberID}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "summa", column = "summa"),
            @Result(property = "subscriberID", column = "ref_subscriber")
    })
    List<Payment> getPaymentsBySubsId(long subscriberID);

    @Insert({"INSERT INTO payments (ref_subscriber, summa) " +
            "VALUES (#{subscriberID}, #{summa})",})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertForSubsID(Payment payment);

    @Update("update payments set summa = #{summa} where ref_subscriber = #{subscriberID}")
    void updatePaymentForSubscriber(Payment payment);

    @Delete("delete from payments where id = #{id}")
    void deleteByID(long id);

    @Delete("delete from payments where id = #{id}")
    void deletePayment(Payment payment);

    @Delete("delete from payments")
    void truncatetable();
}
