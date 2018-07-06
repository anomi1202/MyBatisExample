package interfaces.mappersAnnotation;

import model.Tariff;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TariffMapper {

    @Select("select * from tariff where id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "descr", column = "descr")
    })
    Tariff getTariffById(Integer id);

    @Select("select * from tariff")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "descr", column = "descr")
    })
    List<Tariff> getTariffs();

    @Insert({"INSERT INTO tariff (descr) " +
            "VALUES (#{descr})",})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Tariff tariff);

    @Update("update tariff set descr = #{descr} where id = #{id}")
    void updateTariff(Tariff tariff);
}
