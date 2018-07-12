package interfaces.mappersAnnotation;

import model.Subscriber;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
public interface SubscriberMapper {

    @Select("select * from subscriber where id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "tariff", column = "ref_tariff", one=@One(select = "interfaces.mappersAnnotation.TariffMapper.getTariffById", fetchType = FetchType.EAGER)),
            @Result(property = "paymentList", column = "id",
                    many=@Many(select = "interfaces.mappersAnnotation.PaymentMapper.getPaymentsBySubsId")),
    })
    Subscriber getSubscriberById(int id);

    @Select("select * from subscriber")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "tariff", column = "ref_tariff", one=@One(select = "interfaces.mappersAnnotation.TariffMapper.getTariffById")),
            @Result(property = "paymentList", column = "id",
                    many=@Many(select = "interfaces.mappersAnnotation.PaymentMapper.getPaymentsBySubsId")),
    })
    List<Subscriber> getSubscribers();

    @Select("select count(*) as COUNT from subscriber")
    @Results(value = @Result(column = "COUNT", javaType = Integer.class))
    Integer getCountSubscribers();

    @Insert({"INSERT INTO subscriber (name, ref_tariff) " +
            "VALUES (#{name}, #{tariffID})",})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertIntoSubscriberWithParam(@Param("name") String name, @Param("tariffID") long tariffID);

    @Insert({"INSERT INTO subscriber (name, ref_tariff) " +
            "VALUES (#{name}, #{tariffID})",})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertIntoSubscriber(Subscriber subscriber);

    @Update("update subscriber set name = #{name}, ref_tariff = #{tariffID} where id = #{id}")
    void updateSubscriber(Subscriber subscriber);
}
