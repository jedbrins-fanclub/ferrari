package dk.eamv.ferrari.scenes.sidebar;

import dk.eamv.ferrari.resources.SVGResources;

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

    SidebarButton(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

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