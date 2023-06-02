package dk.eamv.ferrari.scenes.sidebar;

import dk.eamv.ferrari.resources.SVGResources;

/**
 * Created by: Mikkel
 * <p>
 * This enum defines the SidebarButtons used in the application.
 * Each enum value represents a button in the sidebar and holds its label and associated icon.
 */
public enum SidebarButton {
    DASHBOARD("Forside"),
    LOANS("Lån"),
    REPORTS("Rapporter"),
    CARS("Biler"),
    CUSTOMERS("Kunder"),
    SELLERS("Sælgere"),
    SETTINGS("Indstillinger"),
    LOGOUT("Log ud");

    private final String label;

    /**
     * Constructor for the SidebarButton enum.
     * @param label the text to be displayed on the button
     */
    SidebarButton(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Gets the associated icon for the SidebarButton.
     * The icons are fetched from the SVGResources class.
     * @return the SVG icon associated with the button
     */
    public String getIcon() {
        return switch (this) {
            case DASHBOARD -> SVGResources.getDashboardIcon();
            case LOANS -> SVGResources.getLoansIcon();
            case REPORTS -> SVGResources.getReportsIcon();
            case CARS -> SVGResources.getCarsIcon();
            case CUSTOMERS -> SVGResources.getCustomersIcon();
            case SELLERS -> SVGResources.getSellersIcon();
            case SETTINGS -> SVGResources.getSettingsIcon();
            case LOGOUT -> SVGResources.getLogOutIcon();
        };
    }
}