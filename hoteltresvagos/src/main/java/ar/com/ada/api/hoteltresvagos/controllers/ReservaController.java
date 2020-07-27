package ar.com.ada.api.hoteltresvagos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.hoteltresvagos.entities.Reserva;
import ar.com.ada.api.hoteltresvagos.models.response.GenericResponse;
import ar.com.ada.api.hoteltresvagos.services.ReservaService;

@RestController
public class ReservaController {
    
    @Autowired
    ReservaService reservaService;

    /**
     * 
     * @param nombre
     * @return
     */
    @GetMapping("/reservas")
    public List<Reserva> getReservas(@RequestParam(value = "nombre", required = false) String nombre) {
        List<Reserva> lr;

        if (nombre == null) {
            lr = reservaService.getReservas();
        } else {
            lr = reservaService.buscarReservasPorNombre(nombre);
        }

        return lr;
    }

    /**
     * 
     * 
     * @param id
     * @return
     */
    @GetMapping("/reservas/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable int id) {
        Reserva r = reservaService.buscarPorId(id);

        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(r);
    }

    
    @PostMapping("/reservas")
    public ResponseEntity<?> postReserva(@RequestBody Reserva req) {

        GenericResponse r = new GenericResponse();
        boolean resultado = reservaService.crearReserva(req);

        if (resultado) {
            r.isOk = true;
            r.id = req.getReservaId();
            r.message = "Creaste una reserva con éxito.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo crear la reserva.";

            return ResponseEntity.badRequest().body(r);
        }

    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<?> putReserva(@PathVariable int id, @RequestBody Reserva req) {

        GenericResponse r = new GenericResponse();

        Reserva reservaOriginal = reservaService.buscarPorId(id);

        if (reservaOriginal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean resultado = false;
        resultado = reservaService.actualizarReserva(reservaOriginal, req);

        if (resultado) {
            r.isOk = true;
            r.id = req.getReservaId();
            r.message = "Reserva actualizada con éxito.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo actualizar la reserva.";

            return ResponseEntity.badRequest().body(r);
        }

    }

}