package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.DAOMaquinaImpl;
import model.Item;
import model.Maquina;

@Controller
@RequestMapping("/maquina")
public class MaquinaController {

	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private DAOMaquinaImpl maquinaImpl;

	@Autowired
	public MaquinaController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		maquinaImpl = new DAOMaquinaImpl(jdbcTemplate);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String maquina(Model template) {

		return "maquina";
	}

	@RequestMapping(value = "/alta")
	public String altaMaquinaGet(Model template) {
		return "altaMaquina";
	}

	@RequestMapping(value = "/alta", method = RequestMethod.POST)
	public String altaMaquinaPost(@RequestParam(value = "clasificacion") String clasificacion,
			@RequestParam(value = "marca") String marca, @RequestParam(value = "modelo") String modelo,
			@RequestParam(value = "tipoMP") String tipoMP, @RequestParam(value = "diametroMin") String diametroMin,
			@RequestParam(value = "diametroMax") String diametroMax, @RequestParam(value = "merma") String merma,
			Model template) {

		Maquina maquina = new Maquina(clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma);
		if (maquina.validar()) {
			maquinaImpl.alta(maquina);
			return "redirect:" + "/maquina/lista";
		} else {
			template.addAttribute("validacionDiametro", true);
			return "altaMaquina";
		}
	}

	@RequestMapping(value = "/lista")
	public String listaMaquinasGet(Model template) {
		ArrayList<Maquina> listaDeMaquinas = maquinaImpl.consultar();
		template.addAttribute("listaDeMaquinas", listaDeMaquinas);

		return "listaDeMaquinas";
	}

	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.POST)
	public String modificarMaquina(@PathVariable(value = "id") long id, Model template,
			@RequestParam(value = "clasificacion") String clasificacion, @RequestParam(value = "marca") String marca,
			@RequestParam(value = "modelo") String modelo, @RequestParam(value = "tipoMP") String tipoMP,
			@RequestParam(value = "diametroMin") String diametroMin,
			@RequestParam(value = "diametroMax") String diametroMax, @RequestParam(value = "merma") String merma) {

		maquinaImpl.modificar(new Maquina(id, clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma));

		return "redirect:" + "/maquina/lista";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminarMaquina(@PathVariable(value = "id") long id, Model template) {
		maquinaImpl.baja(id);
		return "redirect:" + "/maquina/lista";
	}
}