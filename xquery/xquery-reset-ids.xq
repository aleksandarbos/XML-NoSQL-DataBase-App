xquery version "3.0";

(:~
: User: aleksandar
: Date: 16.6.16.
: Time: 21.27
: To change this template use File | Settings | File Templates.
:)

module namespace xquery-reset-ids = "xquery-reset-ids";
declare namespace pp = "http://www.parlament.gov.rs/propisi";
for $x at $i in doc("/parliament/regulations/2991373610789630903.xml")//pp:Clan
let $newVal as attribute() := attribute Oznaka_clana {$i}
return xdmp:node-replace($x/@Oznaka_clana, $newVal)