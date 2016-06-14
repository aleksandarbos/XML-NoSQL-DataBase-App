xquery version "3.0";

(:~
: User: aleksandar
: Date: 14.6.16.
: Time: 19.53
: To change this template use File | Settings | File Templates.
:)

declare namespace pp = "http://www.parlament.gov.rs/propisi";
for $x in collection("/parliament/regulations")
let $y := fn:root($x)
where $y//@Tip_dokumenta = 'PROPIS'
and $y//pp:Naziv/text() = 'O PROMETU NEPOKRETNOSTI t123'
and $y//pp:Preambula/pp:Status/text() = 'PREDLOZEN'
and $y//pp:Preambula/pp:Predlagac/text() = 'p0@parlament.rs'
and $y//pp:Preambula/pp:Broj_glasova_za >= 0
and $y//pp:Preambula/pp:Broj_glasova_za <= 0
and $y//pp:Preambula/pp:Broj_glasova_protiv >= 0
and $y//pp:Preambula/pp:Broj_glasova_protiv <= 0
and $y//pp:Preambula/pp:Broj_glasova_uzdrzano >= 0
and $y//pp:Preambula/pp:Broj_glasova_uzdrzano <= 0
return $y
