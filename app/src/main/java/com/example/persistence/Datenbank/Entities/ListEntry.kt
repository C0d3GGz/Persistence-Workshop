package com.example.persistence.Datenbank.Entities

import androidx.room.Entity


// TODO: Implementiere Klasse, sodass es von Room in der Datenbank als Tabelle  angelegt wird
//  .
//  Folgende Attributen sollen vorhanden sein: (!!! Namen der Variabeln bite genau so übernehmen)
//      - taskID: Einen ID, der als Primärschlussel der Tabelle dienen soll
//        (Werte für taskID sollen von Room automatisch eingetragen werden)
//      - name (String) : Name des Eintrags
//      - completed (Boolean) : Ob der Eintrag erledigt wurde oder nicht
data class ListEntry (
    val placeholder : String // Platzhalter bitte entfernen
)