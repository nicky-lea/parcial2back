package org.edu.usco.pw.exam_practice.controller;

import org.edu.usco.pw.exam_practice.entities.Clase;
import org.edu.usco.pw.exam_practice.entities.UserEntity;
import org.edu.usco.pw.exam_practice.service.ClaseService;
import org.edu.usco.pw.exam_practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
        return "index"; // La vista para la página de inicio
    }

    @GetMapping("/iniciar")
    public String iniciar() {
        return "iniciar";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("allRoles", Arrays.asList("ADMIN", "ESTUDIANTE", "DOCENTE")); // Roles disponibles
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String username,
            @RequestParam List<String> roles,
            Model model) {

        try {
            userService.registerUser(id, name, password, username, roles);
            model.addAttribute("successMessage", "Usuario registrado exitosamente");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al registrar el usuario: " + e.getMessage());
        }

        return "register";
    }

    @Autowired
    private ClaseService claseService;

    @GetMapping("/clases")
    public String listarClasesest(Model model) {
        List<Clase> clases = claseService.getAllClases(); // Método que deberías implementar en tu servicio
        model.addAttribute("clases", clases);
        return "clases";
    }

    @GetMapping("/rector")
    public String listarClases(Model model) {
        List<Clase> clases = claseService.getAllClases(); // Método que deberías implementar en tu servicio
        model.addAttribute("clases", clases);
        return "rector";
    }

    @GetMapping("/rector/crear")
    public String mostrarFormularioCrearClase(@PathVariable Long userId, Model model) {
        UserEntity docente = userService.findById(userId);;
        return "crear";
    }

    @PostMapping("/clases")
    public String crearClase(String nombre, String descripcion, String horaInicio, String horaFin, Long userId) {
        Clase nuevaClase = new Clase();
        nuevaClase.setName(nombre);
        nuevaClase.setDescription(descripcion);
        nuevaClase.setHent(horaInicio);
        nuevaClase.setHsal(horaFin);

        // Aquí puedes usar el ID del docente para asociarlo a la nueva clase
        UserEntity docente = userService.findById(userId);
        nuevaClase.setUser(docente);

        claseService.guardarClase(nuevaClase);
        return "redirect:/rector";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return "redirect:/rector";
    }


    @GetMapping("/docente")
    public String listarClasesdocente(Model model) {
        List<Clase> clases = claseService.getAllClases(); // Método que deberías implementar en tu servicio
        model.addAttribute("clases", clases);
        return "docente";
    }

    @GetMapping("/docente/editar/{id}")
    public String mostrarFormularioEdiciondoc(@PathVariable Long id, Model model) {
        Clase clase = claseService.getClaseById(id);
        model.addAttribute("clase", clase);
        return "editdoc";
    }

    @PostMapping("/docente/editar/{id}")
    public String actualizarClasedoc(@PathVariable Long id, @ModelAttribute Clase clase,
                                  RedirectAttributes redirectAttributes) {
        try {
            claseService.updateClase(id, clase);
            redirectAttributes.addFlashAttribute("mensaje", "Clase actualizada exitosamente");
            return "redirect:/docente";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editdoc" + id;
        }
    }


}
