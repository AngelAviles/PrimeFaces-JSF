package com.matoosfe.sisfac.admin.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

import com.matoosfe.sisfac.core.AbstractManagedBean;
import com.matoosfe.sisfac.entidad.Producto;
import com.matoosfe.sisfac.entidad.TipoProducto;
import com.matoosfe.sisfac.negocio.ProductoFacade;
import com.matoosfe.sisfac.negocio.TipoProductoFacade;

/**
 * Clase para administrar el formulario de producto
 * 
 * @author angel
 *
 */
@ManagedBean
@ViewScoped
public class ProductoBean extends AbstractManagedBean {

	private Producto producto;
	private Producto productoSel;
	private List<Producto> listaProductos;
	private List<TipoProducto> listaTipoProductos;

	@EJB
	private ProductoFacade adminProducto;
	@EJB
	private TipoProductoFacade adminTipoProducto;

	public ProductoBean() {
		this.producto = new Producto();
		this.listaProductos = new ArrayList<>();
		this.listaTipoProductos = new ArrayList<>();
	}

	/**
	 * @return the Producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the Producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * @return the productoSel
	 */
	public Producto getProductoSel() {
		return productoSel;
	}

	/**
	 * @param productoSel the productoSel to set
	 */
	public void setProductoSel(Producto productoSel) {
		this.productoSel = productoSel;
	}

	/**
	 * @return the listaProducto
	 */
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	/**
	 * @param listaProducto the listaProducto to set
	 */
	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	/**
	 * @return the listaTipoProductos
	 */
	public List<TipoProducto> getListaTipoProductos() {
		return listaTipoProductos;
	}

	/**
	 * @param listaTipoProductos the listaTipoProductos to set
	 */
	public void setListaTipoProductos(List<TipoProducto> listaTipoProductos) {
		this.listaTipoProductos = listaTipoProductos;
	}

	/**
	 * Metodo para guardar un tipo de producto
	 */
	public void guardar() {
		try {
			if (producto.getProProducto() != 0) {
				adminProducto.actualizar(producto);
				anadirMensajeInfo("Registro actualizado exitosamente");
			} else {
				adminProducto.guardar(producto);
				anadirMensajeInfo("Registro guardado exitosamente");
			}
			cargarProductos();
			resetearFormulario();
		} catch (Exception e) {
			anadirMensajeError("No se ha podido guardar el producto: " + e.getMessage());
		}
	}

	public void editar() {
		if (this.productoSel != null) {
			this.producto = productoSel;
		} else {
			anadirMensajeError("Se debe seleccionar un tipo de producto");
		}
	}

	public void eliminar() {
		try {
			if (this.productoSel != null) {
				adminProducto.eliminar(productoSel);
				cargarProductos();
				resetearFormulario();
				anadirMensajeInfo("Registro eliminado exitosamente");
			} else {
				anadirMensajeError("Se debe seleccionar un producto");
			}
		} catch (Exception e) {
			anadirMensajeError("Error al eliminar tipo de producto: " + e.getMessage());
		}
	}

	public void resetearFormulario() {
		this.producto = new Producto();
		this.productoSel = null;
	}

	private void cargarProductos() {
		try {
			this.listaProductos = adminProducto.consultarTodos();
		} catch (Exception e) {
			anadirMensajeError("No se ha podido cargar los productos: " + e.getMessage());
		}
	}
	
	private void cargarTipoProductos() {
		try {
			this.listaTipoProductos = adminTipoProducto.consultarTodos();
		} catch (Exception e) {
			anadirMensajeError("No se ha podido cargar los tipos de productos: " + e.getMessage());
		}
	}

	public void seleccionarRegistro(SelectEvent<Producto> e) {
		this.productoSel = e.getObject();
	}

	@PostConstruct
	public void inicializar() {
		cargarProductos();
		cargarTipoProductos();
	}

}
