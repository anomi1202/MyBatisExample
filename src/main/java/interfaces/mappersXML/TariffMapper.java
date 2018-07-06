package interfaces.mappersXML;

import model.Tariff;

import java.util.List;

public interface TariffMapper {
    Tariff getTariffById(Integer id);
    List<Tariff>  getTariffs();
    void insertTariff(Tariff tariff);
    void updateTariff(Tariff tariff);
}
