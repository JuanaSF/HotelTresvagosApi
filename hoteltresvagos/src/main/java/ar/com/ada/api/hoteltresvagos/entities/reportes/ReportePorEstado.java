package ar.com.ada.api.hoteltresvagos.entities.reportes;

import java.math.BigDecimal;

public interface ReportePorEstado {

    Integer getTipoEstadoId();

    Integer getCantidadReservas();

    BigDecimal getTotalImporteReserva();

    BigDecimal getTotalImportePagado();

    BigDecimal getImporteTotal();

    String getDescripcion();

}