xquery version "3.0";

(:~
: User: aleksandar
: Date: 15.6.16.
: Time: 19.32
: To change this template use File | Settings | File Templates.
:)

declare namespace pp = "http://www.parlament.gov.rs/propisi";
for $node in doc("/parliament/regulations/4027267063675780869.xml")//pp:Clan[@Oznaka_clana = 2]//pp:Stav[@Oznaka_stava = 3]
return xdmp:node-replace($node/text(), text{"DIVLJAAAAAAAAAAAAAACCCCC"});

(:
declare namespace pp = "http://www.parlament.gov.rs/propisi";
for $title in doc("/parliament/regulations/7623921861952747138.xml")//pp:Tacka[@Oznaka_tacke = 2]
return xdmp:node-replace($title/text(), text{"FUCK THE POLICE!!!! OZNAKA TACKE 1"})
:)
(:
xdmp:node-replace(doc("/mydir/doc.txt")/text() ,
text{ concat(doc("/mydir/doc.txt")/text(), "
This is another line of text.") } ) ;
:)

(: for $i in collection("/parliament/amendments") return xdmp:document-delete(fn:document-uri($i)) :)

(: xdmp:document-delete("/parliament/regulations/7623921861952747138.xml") :)

(:
declare namespace pp = "http://www.parlament.gov.rs/propisi";
for $title in doc("/parliament/regulations/4027267063675780869.xml")//pp:Tacka[@Oznaka_tacke = 2]
return xdmp:node-replace($title/text(), text{"FUCK THE POLICE!!!! OZNAKA TACKE 1"})
:)
(:
declare namespace pp = "http://www.parlament.gov.rs/propisi";
                for $node in doc("/parliament/regulations/4027267063675780869.xml")
                //pp:Clan[@Oznaka_clana = 5]
                //pp:Stav[@Oznaka_stava = 7]
                //pp:Tacka[@Oznaka_tacke = 1]
                return xdmp:node-replace($node/text(),
                text{"******* *CLAN 5, STAV 7, TACKA 1 **********"});
:)

(:
xdmp:node-replace(doc("/mydir/doc.txt")/text() ,
text{ concat(doc("/mydir/doc.txt")/text(), "
This is another line of text.") } ) ;
:)