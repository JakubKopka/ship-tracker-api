package dev.kopka.shiptracker.domain.model;

public enum ShipType {
    WING_IN_GROUND("Wing in ground (WIG)"),
    FISHING("Fishing"),
    DREDGING_OR_UNDERWATER_OPS("Dredging or underwater ops"),
    DIVING_OPS("Diving ops"),
    MILITARY_OPS("Military ops"),
    SAILING("Sailing"),
    PLEASURE_CRAFT("Pleasure Craft"),
    HSC("High speed craft (HSC)"),
    PILOT_VESSEL("Pilot Vessel"),
    SEARCH_AND_RESCUE_VESSEL("Search and Rescue vessel"),
    TUG("Tug"),
    PORT_TENDER("Port Tender"),
    ANTI_POLLUTION_EQUIPMENT("Anti-pollution equipment"),
    MEDICAL_TRANSPORT("Medical Transport"),
    PASSENGER("Passenger"),
    CARGO("Cargo"),
    TANKER("Tanker"),
    OTHER_TYPE("Other Type");

    String type;

    ShipType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
