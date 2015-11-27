/**
 *  Copyright 2014-15 by Riccardo Massera (TheCoder4.Eu) and Stephan Rauh (http://www.beyondjava.net).
 *  
 *  This file is part of BootsFaces.
 *  
 *  BootsFaces is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BootsFaces is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with BootsFaces. If not, see <http://www.gnu.org/licenses/>.
 */

package net.bootsfaces.component.column;

import javax.faces.component.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import net.bootsfaces.render.A;
import net.bootsfaces.render.CoreRenderer;
import net.bootsfaces.render.Tooltip;

/** This class generates the HTML code of &lt;b:column /&gt;. */
@FacesRenderer(componentFamily = "net.bootsfaces.component", rendererType = "net.bootsfaces.component.column.Column")
public class ColumnRenderer extends CoreRenderer {

	/**
	 * This methods generates the HTML code of the current b:column.
	 * <code>encodeBegin</code> generates the start of the component. After the,
	 * the JSF framework calls <code>encodeChildren()</code> to generate the
	 * HTML code between the beginning and the end of the component. For
	 * instance, in the case of a panel component the content of the panel is
	 * generated by <code>encodeChildren()</code>. After that,
	 * <code>encodeEnd()</code> is called to generate the rest of the HTML code.
	 * 
	 * @param context
	 *            the FacesContext.
	 * @param component
	 *            the current b:column.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		Column column = (Column) component;

		if (column.isRendered()) {
			ResponseWriter rw = context.getResponseWriter();

			Map<String, Object> attrs = column.getAttributes();

			int colxs = A.toInt(attrs.get("col-xs"));
			int colsm = A.toInt(attrs.get("col-sm"));
			int collg = A.toInt(attrs.get("col-lg"));

			int span = A.toInt(attrs.get(A.SPAN));

			int colmd = (span > 0) ? span : A.toInt(attrs.get("col-md"));
			if ((colxs > 0) || (colsm > 0) || (collg > 0)) {
				colmd = (colmd > 0) ? colmd : 0;
			} else {
				colmd = (colmd > 0) ? colmd : 12;
			}

			int offs = A.toInt(attrs.get("offset"));
			int offsmd = (offs > 0) ? offs : A.toInt(attrs.get("offset-md"));
			int oxs = A.toInt(attrs.get("offset-xs"));
			int osm = A.toInt(attrs.get("offset-sm"));
			int olg = A.toInt(attrs.get("offset-lg"));
			String style = A.asString(attrs.get("style"));
			String sclass = A.asString(attrs.get("styleClass"));

			rw.startElement("div", column);
			Map<String, Object> componentAttrs = new HashMap<String, Object>();

			if (this != null) {
				rw.writeAttribute("id", column.getClientId(), "id");
				Tooltip.generateTooltip(FacesContext.getCurrentInstance(), column.getAttributes(), rw);
				componentAttrs = column.getAttributes();
			}

			StringBuilder sb = new StringBuilder();
			if (colmd > 0 || offsmd > 0) {
				if (colmd > 0) {
					sb.append("col-md-").append(colmd);
				}
				if (offsmd > 0) {
					if (colmd > 0) {
						sb.append(" ");
					}
					sb.append("col-md-offset-" + offsmd);
				}
			}

			if (colxs > 0) {
				sb.append(" col-xs-").append(colxs);
			}
			if (componentAttrs.get("col-xs") != null && colxs == 0) {
				sb.append(" hidden-xs");
			}

			if (colsm > 0) {
				sb.append(" col-sm-").append(colsm);
			}
			if (componentAttrs.get("col-sm") != null && colsm == 0) {
				sb.append(" hidden-sm");
			}

			if (collg > 0) {
				sb.append(" col-lg-").append(collg);
			}
			if (componentAttrs.get("col-lg") != null && collg == 0) {
				sb.append(" hidden-lg");
			}

			if (oxs > 0) {
				sb.append(" col-xs-offset-").append(oxs);
			}
			if (osm > 0) {
				sb.append(" col-sm-offset-").append(osm);
			}
			if (olg > 0) {
				sb.append(" col-lg-offset-").append(olg);
			}

			if (sclass != null) {
				sb.append(" ").append(sclass);
			}
			rw.writeAttribute("class", sb.toString().trim(), "class");
			if (style != null) {
				rw.writeAttribute("style", style, "style");
			}

			if (null != this) {
				
			}
		}
	}
	
	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		Column column = (Column) component;

		if (column.isRendered()) {
			super.encodeChildren(context, component);
		}
	}
	
	public void encodeEnd(FacesContext fc, UIComponent component) throws IOException {
		Column column = (Column) component;

		if (column.isRendered()) {
	        fc.getResponseWriter().endElement("div");
	        Tooltip.activateTooltips(FacesContext.getCurrentInstance(), column);
		}
    }
}
