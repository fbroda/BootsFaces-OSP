/**
 *  Copyright 2014-2017 Riccardo Massera (TheCoder4.Eu), Stephan Rauh (http://www.beyondjava.net) and Dario D'Urzo.
 *
 *  This file is part of BootsFaces.
 *
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
 */
package net.bootsfaces.component.accordion;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import net.bootsfaces.component.panel.Panel;
import net.bootsfaces.render.CoreRenderer;
import net.bootsfaces.render.Responsive;
import net.bootsfaces.render.Tooltip;

/** This class generates the HTML code of &lt;b:accordion /&gt;. */
@FacesRenderer(componentFamily = "net.bootsfaces.component", rendererType = "net.bootsfaces.component.accordion.Accordion")
public class AccordionRenderer extends CoreRenderer {

	/**
	 * Override encodeChildren because the children rendering is driven by the
	 * custom encode of accordion component
	 */
	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// Children are already rendered in encodeBegin()
	}

	/**
	 * Force true to prevent JSF child rendering
	 */
	@Override
	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * This methods generates the HTML code of the current b:accordion.
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
	 *            the current b:accordion.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		Accordion accordion = (Accordion) component;
		ResponseWriter rw = context.getResponseWriter();
		String clientId = accordion.getClientId();
		String accordionClientId = clientId.replace(":", "_");

		List<String> expandedIds = (null != accordion.getExpandedPanels())
				? Arrays.asList(accordion.getExpandedPanels().split(",")) : null;

		rw.startElement("div", accordion);
		String styleClass = accordion.getStyleClass();
		if (null == styleClass) {
			styleClass="panel-group";
		} else {
			styleClass += " panel-group";
		}
		rw.writeAttribute("class", (styleClass + Responsive.getResponsiveStyleClass(accordion, false)).trim(), null);
		writeAttribute(rw, "style", accordion.getStyle());
		rw.writeAttribute("id", accordionClientId, "id");
		Tooltip.generateTooltip(context, component, rw);
		
		beginDisabledFieldset(accordion, rw);

		if (accordion.getChildren() != null && accordion.getChildren().size() > 0) {
			for (UIComponent _child : accordion.getChildren()) {
				if (_child instanceof Panel && ((Panel) _child).isCollapsible()) {
					Panel _childPane = (Panel) _child;
					_childPane.setAccordionParent(accordionClientId);
                                        String childPaneClientId = _childPane.getClientId();
                                        if (_childPane.getClientId().contains(":")) {
                                            String[] parts = _childPane.getClientId().split(":");
                                            if (parts.length == 2)
                                                childPaneClientId = parts[1];
                                        }
					if (null != expandedIds && expandedIds.contains(_childPane.getClientId()))
						_childPane.setCollapsed(false);
					else
						_childPane.setCollapsed(true);
					_childPane.encodeAll(context);
				} else {
					throw new FacesException("Accordion must contain only collapsible panel components", null);
				}
			}
		}
		endDisabledFieldset(accordion, rw);
	}


	/**
	 * This methods generates the HTML code of the current b:accordion.
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
	 *            the current b:accordion.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		ResponseWriter rw = context.getResponseWriter();
		rw.endElement("div");
		Tooltip.activateTooltips(context, component);
	}
}
