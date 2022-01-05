/**
 * 
 */
package com.matoosfe.sisfac.convertidor;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.matoosfe.sisfac.entidad.TipoProducto;
import com.matoosfe.sisfac.negocio.TipoProductoFacade;

/**
 * @author angel
 *
 */
@ManagedBean(name = "convTipPro")
public class ConvTipoProducto implements Converter {

	@EJB
	private TipoProductoFacade adminTipoProducto;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!value.equals("") && !value.equals("Seleccione una opción")) {
			TipoProducto tipPro = adminTipoProducto.consultarPorId(Integer.valueOf(value));
			return tipPro;
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!value.equals("") && !value.equals("Seleccione una opción")) {
			TipoProducto tipPro = (TipoProducto) value;
			return String.valueOf(tipPro.getTipproCodigo());
		} else {
			return null;
		}
	}

}
