package com.matoosfe.sisfac.entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * The persistent class for the detalle_factura database table.
 * 
 */
@Entity
@Table(name="detalle_factura")
@NamedQuery(name="DetalleFactura.findAll", query="SELECT d FROM DetalleFactura d")
public class DetalleFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="detfac_codigo")
	private int detfacCodigo;

	@Column(name="detfac_cantidad")
	private double detfacCantidad;

	@Column(name="detfac_observacion")
	private String detfacObservacion;

	@Column(name="detfac_precio")
	private BigDecimal detfacPrecio;

	@Column(name="detfac_total")
	private BigDecimal detfacTotal;
	
	@Transient
	private int detfacCodigoTmp;

	//bi-directional many-to-one association to Factura
	@ManyToOne
	@JoinColumn(name="fac_codigo")
	private Factura factura;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="pro_producto")
	private Producto producto;

	public DetalleFactura() {
	}

	public int getDetfacCodigo() {
		return this.detfacCodigo;
	}

	public void setDetfacCodigo(int detfacCodigo) {
		this.detfacCodigo = detfacCodigo;
	}

	public double getDetfacCantidad() {
		return this.detfacCantidad;
	}

	public void setDetfacCantidad(double detfacCantidad) {
		this.detfacCantidad = detfacCantidad;
	}

	public String getDetfacObservacion() {
		return this.detfacObservacion;
	}

	public void setDetfacObservacion(String detfacObservacion) {
		this.detfacObservacion = detfacObservacion;
	}

	public BigDecimal getDetfacPrecio() {
		return this.detfacPrecio;
	}

	public void setDetfacPrecio(BigDecimal detfacPrecio) {
		this.detfacPrecio = detfacPrecio;
	}

	public BigDecimal getDetfacTotal() {
		return this.detfacTotal;
	}

	public void setDetfacTotal(BigDecimal detfacTotal) {
		this.detfacTotal = detfacTotal;
	}

	/**
	 * @return the detfacCodigoTmp
	 */
	public int getDetfacCodigoTmp() {
		return detfacCodigoTmp;
	}

	/**
	 * @param detfacCodigoTmp the detfacCodigoTmp to set
	 */
	public void setDetfacCodigoTmp(int detfacCodigoTmp) {
		this.detfacCodigoTmp = detfacCodigoTmp;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(producto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleFactura other = (DetalleFactura) obj;
		return Objects.equals(producto, other.producto);
	}

	@Override
	public String toString() {
		return detfacCodigo + " - " + detfacObservacion;
	}

}