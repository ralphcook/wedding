package rc.wedding.ui;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.vaadin.addon.touchkit.gwt.TouchKitBundleLoaderFactory;
import com.vaadin.addon.touchkit.gwt.client.vcom.DatePickerConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.EmailFieldConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.GeolocatorConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.TabBarConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.VerticalComponentGroupConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationBarConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationButtonConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationManagerConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationViewConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.popover.PopoverConnector;
import com.vaadin.client.extensions.ResponsiveConnector;
import com.vaadin.client.extensions.javascriptmanager.JavaScriptManagerConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.client.ui.csslayout.CssLayoutConnector;
import com.vaadin.client.ui.form.FormConnector;
import com.vaadin.client.ui.formlayout.FormLayoutConnector;
import com.vaadin.client.ui.image.ImageConnector;
import com.vaadin.client.ui.label.LabelConnector;
import com.vaadin.client.ui.link.LinkConnector;
import com.vaadin.client.ui.nativeselect.NativeSelectConnector;
import com.vaadin.client.ui.optiongroup.OptionGroupConnector;
import com.vaadin.client.ui.orderedlayout.VerticalLayoutConnector;
import com.vaadin.client.ui.table.TableConnector;
import com.vaadin.client.ui.textfield.TextFieldConnector;
import com.vaadin.client.ui.ui.UIConnector;
import com.vaadin.client.ui.upload.UploadConnector;
import com.vaadin.client.ui.window.WindowConnector;
import com.vaadin.shared.ui.Connect.LoadStyle;

public class WidgetLoaderFactory extends TouchKitBundleLoaderFactory
{
  private final ArrayList<String> usedConnectors = new ArrayList<>();
  
  public WidgetLoaderFactory()
  {
    addClass(ButtonConnector.class);
    addClass(CssLayoutConnector.class);
    addClass(VerticalLayoutConnector.class);
    addClass(DatePickerConnector.class);
    addClass(EmailFieldConnector.class);
    addClass(ImageConnector.class);
    addClass(FormConnector.class);
    addClass(FormLayoutConnector.class);
    addClass(GeolocatorConnector.class);
    addClass(LabelConnector.class);
    addClass(LinkConnector.class);
    addClass(NativeSelectConnector.class);
    addClass(NavigationBarConnector.class);
    addClass(NavigationButtonConnector.class);
    addClass(NavigationManagerConnector.class);
    addClass(NavigationViewConnector.class);
    addClass(OptionGroupConnector.class);
    addClass(PopoverConnector.class);
    addClass(TabBarConnector.class);
    addClass(TableConnector.class);
    addClass(TextFieldConnector.class);
    addClass(UIConnector.class);
    addClass(UploadConnector.class);
    addClass(VerticalComponentGroupConnector.class);
    addClass(WindowConnector.class);
    addClass(ResponsiveConnector.class);
    addClass(JavaScriptManagerConnector.class);
  }
  
  private void addClass(Class<?> c) { usedConnectors.add(c.getName()); }
  
  @Override
  protected Collection<JClassType> getConnectorsForWidgetset(TreeLogger logger, TypeOracle typeOracle)
    throws UnableToCompleteException
  {
    Collection<JClassType> connectorsForWidgetset
      = super.getConnectorsForWidgetset(logger, typeOracle);
    ArrayList<JClassType> arrayList = new ArrayList<>();
    for (JClassType jClassType : connectorsForWidgetset)
    {
      String qualifiedSourceName = jClassType.getQualifiedSourceName();
      if (usedConnectors.contains(qualifiedSourceName))
      {
        arrayList.add(jClassType);
      }
    }
    return arrayList;
  }
  
  @Override protected LoadStyle getLoadStyle(JClassType connectorType)
  {
    return LoadStyle.EAGER;
  }
}
