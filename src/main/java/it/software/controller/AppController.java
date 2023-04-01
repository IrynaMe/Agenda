package it.software.controller;

import it.software.model.Agenda;
import it.software.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {

    @Autowired
    AgendaRepository agendaRepository;

    //menu
    @GetMapping("/")
    public ModelAndView viewIndex(Model model) {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    //form to insert a new event
    @GetMapping("/insert")
    public ModelAndView insert(Model model) {
        ModelAndView mav = new ModelAndView("insert");
        return mav;
    }

    //save a new event getting data from form
    @PostMapping("/save")
    private ModelAndView salvaEvento(
            @RequestParam String titolo,
            @RequestParam String descrizione,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            Model model) {
        try {
            if (titolo == null || descrizione == null || date == null || time == null) {
                throw new Exception("Titolo, descrizione, DateEvento, OraEvento non possono essere vuoti");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("error");
        }
        Agenda agenda = new Agenda();
        agenda.setTitolo(titolo);
        agenda.setDescrizione(descrizione);
        agenda.setDataEvento(date);
        agenda.setOraEvento(time);
        agenda.setTimeRegistrato(LocalDateTime.now());
        agendaRepository.save(agenda);

        ModelAndView mav = new ModelAndView("confirm");
        mav.addObject("agenda", agenda);

        return mav;
    }


    //shows all the events
    @GetMapping("/all")
    private ModelAndView getAll() {
        List<Agenda> listAgenda = new ArrayList<>();
        agendaRepository.findAll().forEach(agenda -> listAgenda.add(agenda));
        ModelAndView mav = new ModelAndView("events");
        mav.addObject("listAgenda", listAgenda);
        return mav;
    }

    //insert id of the event to be modified
    @GetMapping("/modify")
    public ModelAndView modify(Model model) {
        ModelAndView mav = new ModelAndView("id");
        return mav;
    }

    //search event by id -> form to update
    @PostMapping("/find")
    public ModelAndView trovaEvento(@RequestParam Integer id, Model model)  {
        try {
            if (id == null ) {
                throw new Exception("Id non puo essere vuoto");
            }
        Agenda agenda = agendaRepository.findById(id).get();
        model.addAttribute("agenda", agenda);
        return new ModelAndView("update");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("error");
        }
    }

    //form to modify ->updates changes
    @PostMapping("/update")
    public ModelAndView updateEvento(Agenda agenda,
                                     @RequestParam String titolo,
                                     @RequestParam String descrizione,
                                     @RequestParam LocalDate date,
                                     @RequestParam LocalTime time,
                                     Model model) {
        try {
            if (titolo == null || descrizione == null || date == null || time == null) {
                throw new Exception("Titolo, descrizione, DateEvento, OraEvento non possono essere vuoti");
            }
            agenda.setTitolo(titolo);
            agenda.setDescrizione(descrizione);
            agenda.setDataEvento(date);
            agenda.setOraEvento(time);
            agenda.setTimeRegistrato(LocalDateTime.now());
            agendaRepository.save(agenda);

            model.addAttribute("agenda", agenda);
            return new ModelAndView("confirm");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("error");
        }
    }



    //insert id of the event to be deleted
    @GetMapping("/delete")
    public ModelAndView idToDelete(Model model) {
        ModelAndView mav = new ModelAndView("delete");
        return mav;
    }


    //delete by id & confirm
    @PostMapping("/deleted")
    public ModelAndView cancellaEvento(@RequestParam Integer id, Model model) {
        try {
            if (id == null ) {
                throw new Exception("Id non puo essere vuoto");
            }
        Agenda agenda = agendaRepository.findById(id).get();
        model.addAttribute("agenda", agenda);
        agendaRepository.delete(agenda);
        return new ModelAndView("deleted");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("error");
        }
    }


}//
