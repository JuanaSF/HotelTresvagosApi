package ar.com.ada.api.hoteltresvagos.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.hoteltresvagos.entities.*;
import ar.com.ada.api.hoteltresvagos.entities.reportes.*;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // En este caso se hace un JPQL por el nombre del huesped
    @Query(value = "SELECT * FROM reserva r inner join huesped h on h.huesped_id = r.huesped_id where nombre like CONCAT('%', :nombre,'%')", nativeQuery = true)
    List<Reserva> findByNombreHuesped(@Param("nombre") String nombre);

    @Query(value = "SELECT e.tipo_estado_id tipoEstadoId, e.descripcion, count(r.reserva_id) cantidadReservas, sum(r.importe_reserva) totalImporteReserva, sum(r.importe_pagado) totalImportePagado, sum(r.importe_total) importeTotal FROM reserva r INNER JOIN tipo_estado e ON r.tipo_estado_id = e.tipo_estado_id GROUP BY e.tipo_estado_id, e.descripcion", nativeQuery = true)
    List<ReportePorEstado> getReporteReservasPorEstado();

    @Query(value = "select h.huesped_id huespedId, h.nombre, sum(r.importe_reserva) importeReserva, sum(r.importe_pagado) importePagado, sum(r.importe_total) importeTotal from huesped h inner join reserva r on h.huesped_id = r.huesped_id group by h.huesped_id, h.nombre", nativeQuery = true)
    List<ReportePorHuesped> getReporteReservasPorHuespedes();
}
