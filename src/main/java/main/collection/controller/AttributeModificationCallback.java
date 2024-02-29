package main.collection.controller;

import main.collection.metier.Attribut;

public interface AttributeModificationCallback {
    void onAttributeModified(Attribut modifiedAttribut);

}
