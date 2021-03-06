/**
 * 
 */
package com.matoosfe.sisfac.facturacion.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.matoosfe.sisfac.core.AbstractManagedBean;
import com.matoosfe.sisfac.entidad.Cliente;
import com.matoosfe.sisfac.entidad.DetalleFactura;
import com.matoosfe.sisfac.entidad.Factura;
import com.matoosfe.sisfac.entidad.Producto;
import com.matoosfe.sisfac.negocio.ClienteFacade;
import com.matoosfe.sisfac.negocio.FacturaFacade;
import com.matoosfe.sisfac.negocio.ProductoFacade;
import com.matoosfe.sisfac.util.ConstanteWeb;

/**
 * @author angel
 *
 */
@ManagedBean
@ViewScoped
public class FacturaBean extends AbstractManagedBean {

	private Factura factura;
	private Factura facturaSel;
	private List<Factura> listaFacturas;
	private List<DetalleFactura> listaDetalles;
	private Cliente cliente;
	private DetalleFactura detalle;
	private DetalleFactura detalleSel;
	private Producto producto;
	private String busquedaPor;
	private String valorBusPor;

	private int codTmpFac;

	@EJB
	private FacturaFacade adminFactura;
	@EJB
	private ClienteFacade adminCliente;
	@EJB
	private ProductoFacade adminProducto;

	public FacturaBean() {
		this.factura = new Factura();
		this.listaFacturas = new ArrayList<Factura>();
		this.listaDetalles = new ArrayList<DetalleFactura>();
		this.cliente = new Cliente();
		this.detalle = new DetalleFactura();
		this.producto = new Producto();
	}

	/**
	 * @return the factura
	 */
	public Factura getFactura() {
		return factura;
	}

	/**
	 * @param factura the factura to set
	 */
	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	/**
	 * @return the facturaSel
	 */
	public Factura getFacturaSel() {
		return facturaSel;
	}

	/**
	 * @param facturaSel the facturaSel to set
	 */
	public void setFacturaSel(Factura facturaSel) {
		this.facturaSel = facturaSel;
	}

	/**
	 * @return the listaFacturas
	 */
	public List<Factura> getListaFacturas() {
		return listaFacturas;
	}

	/**
	 * @param listaFacturas the listaFacturas to set
	 */
	public void setListaFacturas(List<Factura> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	/**
	 * @return the listaDetalles
	 */
	public List<DetalleFactura> getListaDetalles() {
		return listaDetalles;
	}

	/**
	 * @param listaDetalles the listaDetalles to set
	 */
	public void setListaDetalles(List<DetalleFactura> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the detalle
	 */
	public DetalleFactura getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(DetalleFactura detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * @return the detalleSel
	 */
	public DetalleFactura getDetalleSel() {
		return detalleSel;
	}

	/**
	 * @param detalleSel the detalleSel to set
	 */
	public void setDetalleSel(DetalleFactura detalleSel) {
		this.detalleSel = detalleSel;
	}

	/**
	 * @return the codTmpFac
	 */
	public int getCodTmpFac() {
		return codTmpFac;
	}

	/**
	 * @param codTmpFac the codTmpFac to set
	 */
	public void setCodTmpFac(int codTmpFac) {
		this.codTmpFac = codTmpFac;
	}

	/**
	 * @return the busquedaPor
	 */
	public String getBusquedaPor() {
		return busquedaPor;
	}

	/**
	 * @param busquedaPor the busquedaPor to set
	 */
	public void setBusquedaPor(String busquedaPor) {
		this.busquedaPor = busquedaPor;
	}

	/**
	 * @return the valorBusPor
	 */
	public String getValorBusPor() {
		return valorBusPor;
	}

	/**
	 * @param valorBusPor the valorBusPor to set
	 */
	public void setValorBusPor(String valorBusPor) {
		this.valorBusPor = valorBusPor;
	}

	/**
	 * Metodo para guardar una factua
	 */
	public void guardar() {
		try {
			if (cliente != null) {
				if (!listaDetalles.isEmpty()) {
					factura.setCliente(cliente);
					factura.setFacEstado(ConstanteWeb.EMITIDO.getValorNumerico());
					// 3 - CASCADE
					factura.setDetalleFacturas(listaDetalles);
					adminFactura.guardar(factura);

					anadirMensajeInfo("Factura guardada correctamente");
					cargarFacturas();
					resetearFormulario();
				} else {
					anadirMensajeError("Se debe ingresar al menos un detalle");
				}
			} else {
				anadirMensajeError("Se debe ingresar un cliente");
			}
		} catch (Exception e) {
			anadirMensajeError("No se ha podido guardar la factura: " + e.getMessage());
		}
	}

	public void anular() {
		try {
			if (facturaSel != null) {
				facturaSel.setFacEstado(ConstanteWeb.ANULADO.getValorNumerico());
				adminFactura.actualizar(facturaSel);
				anadirMensajeInfo("Se anul?? correctamente la factura: " + facturaSel.getFacNumero());
				cargarFacturas();
				resetearFormulario();
			} else {
				anadirMensajeError("Se debe seleccionar una factura");
			}
		} catch (Exception e) {
			anadirMensajeError("No se pudo anular la factura: " + e.getMessage());
		}
	}

	public void eliminar() {
		try {
			if (this.facturaSel != null) {
				adminFactura.eliminar(facturaSel);
				anadirMensajeInfo("Se elimin?? correctamente la factura");
				cargarFacturas();
				resetearFormulario();
			} else {
				anadirMensajeError("Se debe seleccionar una factura");
			}
		} catch (Exception e) {
			anadirMensajeError("Error al eliminar la factura: " + e.getMessage());
		}
	}

	public void resetearFormulario() {
		this.factura = new Factura();
		this.cliente = new Cliente();
		this.facturaSel = null;
		this.listaDetalles.clear();
	}

	private void cargarFacturas() {
		try {
			this.listaFacturas = adminFactura.consultarTodos();
		} catch (Exception e) {
			anadirMensajeError("No se ha podido cargar las facturas: " + e.getMessage());
		}
	}

	public void buscarCliente() {
		try {
			cliente = adminCliente.buscarClientePorIdentificacion(cliente.getCliIdentificacion());
			if (cliente == null) {
				anadirMensajeError("Cliente no encontrado");
				cliente = new Cliente();
			}
		} catch (Exception e) {
			anadirMensajeError("Error al buscar el cliente: " + e.getMessage());
		}
	}

	public void seleccionarFactura(SelectEvent<Factura> e) {
		this.facturaSel = e.getObject();
	}

	public void seleccionarDetalleFactura(SelectEvent<DetalleFactura> e) {
		this.detalleSel = e.getObject();
	}

	public void anadirDetalle() {
		// 2 - CASCADE
		detalle.setFactura(factura);
		detalle.setProducto(producto);
		detalle.setDetfacCodigoTmp(++codTmpFac);

		if (listaDetalles.isEmpty()) {
			this.listaDetalles.add(detalle);
		} else {
			if (listaDetalles.contains(detalle)) {
				anadirMensajeError("El producto ya se encuestra ingresado");
			} else {
				this.listaDetalles.add(detalle);
			}
		}

		cancelarDetalle();
	}

	public void calcularTotalDetalle() {
		BigDecimal totalDetalle = new BigDecimal(0.0);
		totalDetalle = detalle.getDetfacPrecio().multiply(new BigDecimal(detalle.getDetfacCantidad()));
		this.detalle.setDetfacTotal(totalDetalle);
	}

	public void eliminarDetalle() {
		if (detalleSel != null) {
			this.listaDetalles.remove(detalleSel);
		} else {
			anadirMensajeError("Se debe seleccionar un detalle");
		}
	}

	public void cancelarDetalle() {
		this.detalle = new DetalleFactura();
		this.producto = null;
	}

	public void actualizarPrecioProducto() {
		this.detalle.setDetfacPrecio(producto.getProPrecio());
	}

	public List<Producto> completarProductos(String query) {
		List<Producto> productos = adminProducto.consultarTodos();
		List<Producto> productosFiltrados = new ArrayList<Producto>();

		for (int i = 0; i < productos.size(); i++) {
			Producto proTmp = productos.get(i);
			if (proTmp.getProNombre().startsWith(query)) {
				productosFiltrados.add(proTmp);
			}
		}

		return productosFiltrados;
	}

	public void buscarFacturas() {
		try {
			if (busquedaPor != null) {
				this.listaFacturas = adminFactura.buscarFacturas(busquedaPor, valorBusPor);
				anadirMensajeInfo("Se han encontrado " + listaFacturas.size() + " facturas con ese criterio");
			} else {
				this.listaFacturas = adminFactura.consultarTodos();
			}

			busquedaPor = null;
			valorBusPor = null;
		} catch (Exception e) {
			anadirMensajeError("Se tuvo un incoveniente al buscar las facturas: " + e.getMessage());
		}
	}

	@PostConstruct
	public void inicializar() {
		// cargarFacturas();
	}

}
