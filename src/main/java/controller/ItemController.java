package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dao.DAOItemImpl;
import dao.DAOMaquinaImpl;
import model.CliEnOb;
import model.Cliente;
import model.CodigoMP;
import model.Fecha;
import model.Formato;
import model.IngresoMP;
import model.IngresoMP_temp;
import model.IngresoRemitos;
import model.Item;
import model.Maquina;
import model.Motivo;
import model.Obra;
import model.Pedido;
import model.PedidoPSS;
import model.Remito;
import model.RemitoPedido;
import model.Stock;
import model.TablaCalculos;
import model.Usuario;
import model.UsuarioAndroid;
import model.pedFormatoRemito;





@Controller
@RequestMapping("/item")
public class ItemController {

	
	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private DAOItemImpl itemImpl;
	
	@Autowired
	public ItemController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		itemImpl = new DAOItemImpl(jdbcTemplate);
	}	
	


	
	
	
	
}

