package com.xceptance.common.csvutils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import com.xceptance.common.util.SimpleArrayList;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class CsvUtilsBenchmarkParseExtended
{
    private String[] lines;
    private List<char[]> clines = new ArrayList<>();
    
    @Setup
    @Before
    public void setup()
    {
        lines = new String[] {
                "C,config.build,1571756299123,0,false",
                "R,Homepage.1,1571756299125,190,false,976,40740,200,https://production-nam-torrid.demandware.net/,text/html,8,26,0,165,25,190,,,,,0,,",
                "R,Homepage.2,1571756299427,57,false,1247,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=TORRID+%7C+BOGO+50%25+OFF&dwac=0.09836953061733211&r=5205092883373519364&ref=,image/gif,0,0,56,0,56,56,,,,,0,,",
                "A,Homepage,1571756299124,360,false",
                "R,GoToMyAccount.1,1571756305533,111,false,928,15938,200,https://production-nam-torrid.demandware.net/s/torrid/account,text/html,0,0,110,0,110,110,,,,,0,,",
                "R,GoToMyAccount.2,1571756305770,56,false,1327,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Faccount&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.5847818852446158&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2F,image/gif,0,0,56,0,56,56,,,,,0,,",
                "A,GoToMyAccount,1571756305532,295,false",
                "R,MemberSignup.1,1571756312565,83,false,1008,589,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-CheckOnlineAccount?email=xc32436c2a8825%40varmail.de,application/json,0,0,81,0,81,81,,,,,0,,",
                "R,MemberSignup.2,1571756312648,153,false,1208,15921,302,https://production-nam-torrid.demandware.net/s/torrid/account?dwcont=C148929874,text/html,0,0,152,0,152,152,,,,,0,,",
                "R,MemberSignup.3,1571756312801,143,false,1005,39188,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-GuestAccount?guest=true,text/html,0,0,142,1,142,143,,,,,0,,",
                "R,MemberSignup.4,1571756312971,118,false,1050,583,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-CheckPhoneNumber?phone=5148439764,text/html,0,0,117,0,117,117,,,,,0,,",
                "R,MemberSignup.5,1571756313093,60,false,1110,618,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Responsive-IsMobileCustomerGroup,application/json,0,0,59,0,59,59,,,,,0,,",
                "R,MemberSignup.6,1571756313262,59,false,1477,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FAccount-GuestAccount%3Fguest%3Dtrue&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.7073761500883933&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Faccount,image/gif,0,0,58,0,58,58,,,,,0,,",
                "A,MemberSignup,1571756312564,756,false",
                "R,Register.1,1571756319658,207,false,1050,39195,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-GuestAccount?guest=true&term=6+Wa,text/html,0,0,205,0,205,205,,,,,0,,",
                "R,Register.2,1571756319865,153,false,1052,39101,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-GuestAccount?guest=true&term=6+Wall,text/html,0,0,150,3,150,153,,,,,0,,",
                "R,Register.3,1571756320018,215,false,1054,38985,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-GuestAccount?guest=true&term=6+Wall+S,text/html,0,0,215,0,215,215,,,,,0,,",
                "R,Register.4,1571756320234,146,false,1055,38993,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-GuestAccount?guest=true&term=6+Wall+St,text/html,0,0,142,3,142,145,,,,,0,,",
                "R,Register.5,1571756320386,66,false,1110,618,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Responsive-IsMobileCustomerGroup,application/json,0,0,64,0,64,64,,,,,0,,",
                "R,Register.6,1571756320456,62,false,1110,618,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Responsive-IsMobileCustomerGroup,application/json,0,0,61,0,61,61,,,,,0,,",
                "R,Register.7,1571756320518,1921,false,1937,31216,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Account-GuestAccount/C148981667,text/html,0,0,1915,6,1915,1921,,,,,0,,",
                "R,Register.8,1571756322578,56,false,1596,599,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FAccount-GuestAccount%2FC148981667&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.0158171723405226&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FAccount-GuestAccount%3Fguest%3Dtrue,image/gif,0,0,56,0,56,56,,,,,0,,",
                "A,Register,1571756319656,2978,false",
                "R,Logout.1,1571756327970,133,false,1099,2342,302,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Login-Logout,text/html,0,0,130,1,130,131,,,,,0,,",
                "R,Logout.2,1571756328103,107,false,972,16051,200,https://production-nam-torrid.demandware.net/s/torrid/account,text/html,0,0,107,0,107,107,,,,,0,,",
                "R,Logout.3,1571756328327,58,false,1448,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Faccount&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.8620258092737366&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FAccount-GuestAccount%2FC148981667&email=xc32436c2a8825%40varmail.de,image/gif,0,0,58,0,58,58,,,,,0,,",
                "A,Logout,1571756327970,416,false",
                "R,GoToHomepage.1,1571756333685,57,false,947,447,301,https://production-nam-torrid.demandware.net/s/torrid/hpredirect,,0,0,54,0,54,54,,,,,0,,",
                "R,GoToHomepage.2,1571756333741,109,false,928,35314,200,https://production-nam-torrid.demandware.net/,text/html,0,0,109,0,109,109,,,,,0,,",
                "R,GoToHomepage.3,1571756334007,66,false,1353,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=TORRID+%7C+BOGO+50%25+OFF&dwac=0.3949834826622327&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Faccount&email=xc32436c2a8825%40varmail.de,image/gif,0,0,64,0,64,64,,,,,0,,",
                "A,GoToHomepage,1571756333684,388,false",
                "R,SelectCategory.1,1571756339527,152,false,948,35032,200,https://production-nam-torrid.demandware.net/s/torrid/fangirl/shop-by-style/swim/,text/html,0,0,109,41,109,150,,,,,0,,",
                "R,SelectCategory.2,1571756339790,65,false,1456,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Ffangirl%2Fshop-by-style%2Fswim%2F&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Pop+Culture+Swimwear%3A+Plus+Size+Pop+Culture+Swimwear+%7C+Torrid&dwac=0.18413054540499618&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2F&email=xc32436c2a8825%40varmail.de,image/gif,0,0,65,0,65,65,,,,,0,,",
                "A,SelectCategory,1571756339527,328,false",
                "R,SortBy.1,1571756344582,321,false,1005,8703,200,https://production-nam-torrid.demandware.net/s/torrid/fangirl/shop-by-style/swim/?srule=PriceHighToLow&sz=60&start=0&format=ajax,text/html,0,0,319,1,319,320,,,,,0,,",
                "A,SortBy,1571756344582,327,false",
                "R,ProductDetailsPage.1,1571756349591,546,false,1118,44800,200,https://production-nam-torrid.demandware.net/s/torrid/product/her-universe-disney-the-little-mermaid-ursula-wireless-one-piece-swimsuit/11656503.html?cgid=Fangirl_ShopByStyle_Swim#srule=PriceHighToLow&sz=60&start=2,text/html,0,0,543,1,543,544,,,,,0,,",
                "R,ProductDetailsPage.2,1571756350182,64,false,1179,581,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Productnav?format=ajax&pid=11656503&srule=PriceHighToLow&sz=60&start=2,text/html,0,0,64,0,64,64,,,,,0,,",
                "R,ProductDetailsPage.3,1571756350348,74,false,1846,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fproduct%2Fher-universe-disney-the-little-mermaid-ursula-wireless-one-piece-swimsuit%2F11656503.html%3Fcgid%3DFangirl_ShopByStyle_Swim%23srule%3DPriceHighToLow%26sz%3D60%26start%3D2&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Her+Universe+Disney+The+Little+Mermaid+Ursula+Wireless+One-Piece+Swimsuit+-+null&dwac=0.3103350382480574&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Ffangirl%2Fshop-by-style%2Fswim%2F%23srule%3DPriceHighToLow%26sz%3D60%26start%3D0%26&email=xc32436c2a8825%40varmail.de,image/gif,0,0,74,0,74,74,,,,,0,,",
                "A,ProductDetailsPage,1571756349591,831,false",
                "R,ConfigureProduct.1,1571756356868,209,false,1250,6074,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11656503&dwvar_11656503_size=1&dwvar_11656503_color=DEEP+BLACK&cgid=Fangirl_ShopByStyle_Swim&Quantity=1&format=ajax&setQty=true,text/html,0,0,207,0,207,207,,,,,0,,",
                "A,ConfigureProduct,1571756356865,227,false",
                "R,AddToCart.1,1571756363832,132,false,1238,6111,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11656503&dwvar_11656503_size=0&dwvar_11656503_color=DEEP+BLACK&cgid=Fangirl_ShopByStyle_Swim&Quantity=1&format=ajax,text/html,0,0,130,1,130,131,,,,,0,,",
                "R,AddToCart.2,1571756363965,170,false,1266,2582,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Cart-AddProduct?format=ajax,text/html,0,0,170,0,170,170,,,,,0,,",
                "A,AddToCart,1571756363797,340,false",
                "R,SearchSuggestion.1,1571756369758,70,false,1507,871,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=i+-98237541184,text/html,0,0,68,0,68,68,,,,,0,,",
                "R,SearchSuggestion.2,1571756369829,63,false,1508,872,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iD+-98237541184,text/html,0,0,63,0,63,63,,,,,0,,",
                "R,SearchSuggestion.3,1571756369893,58,false,1511,875,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJ+-98237541184,text/html,0,0,58,0,58,58,,,,,0,,",
                "R,SearchSuggestion.4,1571756369952,61,false,1512,875,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJj+-98237541184,text/html,0,0,60,1,60,61,,,,,0,,",
                "R,SearchSuggestion.5,1571756370014,62,false,1514,915,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxi+-98237541184,text/html,0,0,60,0,60,60,,,,,0,,",
                "R,SearchSuggestion.6,1571756370076,71,false,1517,885,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQ+-98237541184,text/html,0,0,70,0,70,70,,,,,0,,",
                "R,SearchSuggestion.7,1571756370147,62,false,1519,887,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQow+-98237541184,text/html,0,0,62,0,62,62,,,,,0,,",
                "R,SearchSuggestion.8,1571756370211,59,false,1521,889,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMc+-98237541184,text/html,0,0,59,0,59,59,,,,,0,,",
                "R,SearchSuggestion.9,1571756370270,62,false,1523,890,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca++-98237541184,text/html,0,0,62,0,62,62,,,,,0,,",
                "R,SearchSuggestion.10,1571756370334,60,false,1524,891,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+S+-98237541184,text/html,0,0,60,0,60,60,,,,,0,,",
                "R,SearchSuggestion.11,1571756370394,61,false,1527,893,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbM+-98237541184,text/html,0,0,61,0,61,61,,,,,0,,",
                "R,SearchSuggestion.12,1571756370457,64,false,1529,895,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbMLv+-98237541184,text/html,0,0,64,0,64,64,,,,,0,,",
                "R,SearchSuggestion.13,1571756370521,61,false,1531,897,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbMLvUO+-98237541184,text/html,0,0,61,0,61,61,,,,,0,,",
                "R,SearchSuggestion.14,1571756370583,126,false,1532,931,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbMLvUOW+-98237541184,text/html,0,0,126,0,126,126,,,,,0,,",
                "R,SearchSuggestion.15,1571756370710,61,false,1535,901,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbMLvUOWJvJ+-98237541184,text/html,0,0,61,0,61,61,,,,,0,,",
                "R,SearchSuggestion.16,1571756370773,64,false,1536,903,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbMLvUOWJvJz+-98237541184,text/html,0,0,64,0,64,64,,,,,0,,",
                "R,SearchSuggestion.17,1571756370838,61,false,1538,905,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Search-NoSearchSuggest?nosearchcontenttext=iDqQJjxikaQowMca+SrbMLvUOWJvJzcD+-98237541184,text/html,0,0,61,0,61,61,,,,,0,,",
                "A,SearchSuggestion,1571756369758,1141,false",
                "R,SearchMiss.1,1571756376623,354,false,1480,32170,200,https://production-nam-torrid.demandware.net/s/torrid/search?q=iDqQJjxikaQowMca+SrbMLvUOWJvJzcD,text/html,0,0,348,5,348,353,,,,,0,,",
                "R,SearchMiss.2,1571756377087,65,false,1977,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fsearch%3Fq%3DiDqQJjxikaQowMca%2BSrbMLvUOWJvJzcD&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.0956168484875155&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fproduct%2Fher-universe-disney-the-little-mermaid-ursula-wireless-one-piece-swimsuit%2F11656503.html%3Fcgid%3DFangirl_ShopByStyle_Swim%23srule%3DPriceHighToLow%26sz%3D60%26start%3D2&email=xc32436c2a8825%40varmail.de,image/gif,0,0,64,0,64,64,,,,,0,,",
                "C,Search - Miss,1571756376623,380,false",
                "A,SearchMiss,1571756376622,530,false",
                "R,SearchHits.1,1571756381868,216,false,1342,42826,200,https://production-nam-torrid.demandware.net/s/torrid/search?q=TROUSERS+LEG+,text/html,0,0,213,1,213,214,,,,,0,,",
                "R,SearchHits.2,1571756382203,60,false,1808,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fsearch%3Fq%3DTROUSERS%2BLEG%2B&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.8827945566793245&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fsearch%3Fq%3DiDqQJjxikaQowMca%2BSrbMLvUOWJvJzcD&email=xc32436c2a8825%40varmail.de,image/gif,0,0,59,0,59,59,,,,,0,,",
                "C,Search - Hits,1571756381867,251,false",
                "A,SearchHits,1571756381820,443,false",
                "R,SortBy.1,1571756387131,306,false,1357,12812,200,https://production-nam-torrid.demandware.net/s/torrid/search?q=TROUSERS%20LEG&srule=Custom_NewArrival%2BManual&sz=60&start=0&format=ajax,text/html,0,0,304,0,304,304,,,,,0,,",
                "A,SortBy,1571756387131,319,false",
                "R,QuickView.1,1571756393797,132,false,1428,9124,200,https://production-nam-torrid.demandware.net/s/torrid/product/wide-leg-tie-front-crepe-pant---black/11455867.html?source=quickview&format=ajax,text/html,0,0,130,0,130,130,,,,,0,,",
                "A,QuickView,1571756393796,143,false",
                "R,ConfigureProduct.1,1571756399996,264,false,1519,4439,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11455867&dwvar_11455867_size=6&dwvar_11455867_color=DEEP+BLACK&source=quickview&cgid=&Quantity=1&format=ajax&setQty=true,text/html,0,0,262,0,262,262,,,,,0,,",
                "R,ConfigureProduct.2,1571756400267,315,false,1546,4746,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11455867&dwvar_11455867_inseam=TALL&dwvar_11455867_size=6&dwvar_11455867_color=DEEP+BLACK&source=quickview&cgid=&Quantity=1&format=ajax&setQty=true,text/html,0,0,315,0,315,315,,,,,0,,",
                "A,ConfigureProduct,1571756399992,601,false",
                "R,AddToCart.1,1571756404745,199,false,1535,4751,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11455867&dwvar_11455867_size=00&dwvar_11455867_inseam=TALL&dwvar_11455867_color=DEEP+BLACK&source=quickview&cgid=&Quantity=1&format=ajax,text/html,0,0,196,1,196,197,,,,,0,,",
                "R,AddToCart.2,1571756404945,348,false,1518,2476,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Cart-AddProduct?format=ajax,text/html,0,0,348,0,348,348,,,,,0,,",
                "A,AddToCart,1571756404699,596,false",
                "R,SearchHits.1,1571756411812,250,false,1392,50339,200,https://production-nam-torrid.demandware.net/s/torrid/search?q=Black+White+Dress,text/html,0,0,240,8,240,248,,,,,0,,",
                "R,SearchHits.2,1571756412230,64,false,1885,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fsearch%3Fq%3DBlack%2BWhite%2BDress&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.463778938700635&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fsearch%3Fq%3DTROUSERS%2BLEG%2B%23q%3DTROUSERS%2520LEG%26srule%3DCustom_NewArrival%252BManual%26sz%3D60%26start%3D0%26&email=xc32436c2a8825%40varmail.de,image/gif,0,0,64,0,64,64,,,,,0,,",
                "C,Search - Hits,1571756411812,294,false",
                "A,SearchHits,1571756411810,483,false",
                "R,RefineBySize.1,1571756416075,343,false,1343,13302,200,https://production-nam-torrid.demandware.net/s/torrid/search?q=Black%20White%20Dress&prefn1=size&prefv1=12&format=ajax,text/html,0,0,341,0,341,341,,,,,0,,",
                "A,RefineBySize,1571756416075,358,false",
                "R,ProductDetailsPage.1,1571756423225,696,false,1426,46472,200,https://production-nam-torrid.demandware.net/s/torrid/product/special-occasion-ivory-off-shoulder-sequin-lace-gown/11818822.html#q=Black+White+Dress&prefn1=size&prefv1=12&start=9,text/html,0,0,690,4,690,694,,,,,0,,",
                "R,ProductDetailsPage.2,1571756423970,79,false,1511,581,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Productnav?format=ajax&pid=11818822&q=Black%2BWhite%2BDress&prefn1=size&prefv1=12&start=9,text/html,0,0,79,0,79,79,,,,,0,,",
                "R,ProductDetailsPage.3,1571756424168,59,false,2126,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fproduct%2Fspecial-occasion-ivory-off-shoulder-sequin-lace-gown%2F11818822.html%23q%3DBlack%2BWhite%2BDress%26prefn1%3Dsize%26prefv1%3D12%26start%3D9&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Special+Occasion+Ivory+Off+Shoulder+Sequin+%26+Lace+Gown+-+null&dwac=0.8855887890833076&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fsearch%3Fq%3DBlack%2BWhite%2BDress%23q%3DBlack%2520White%2520Dress%26prefn1%3Dsize%26prefv1%3D12%26&email=xc32436c2a8825%40varmail.de,image/gif,0,0,57,0,57,57,,,,,0,,",
                "A,ProductDetailsPage,1571756423225,1002,false",
                "R,ConfigureProduct.1,1571756430088,134,false,1542,4821,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11818822&dwvar_11818822_size=12&dwvar_11818822_color=CLOUD+DANCER&cgid=&Quantity=1&format=ajax&setQty=true,text/html,0,0,133,0,133,133,,,,,0,,",
                "A,ConfigureProduct,1571756430085,145,false",
                "R,AddToCart.1,1571756436579,140,false,1530,4817,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Product-Variation?pid=11818822&dwvar_11818822_size=10&dwvar_11818822_color=CLOUD+DANCER&cgid=&Quantity=1&format=ajax,text/html,0,0,139,0,139,139,,,,,0,,",
                "R,AddToCart.2,1571756436719,179,false,1555,2691,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Cart-AddProduct?format=ajax,text/html,0,0,179,0,179,179,,,,,0,,",
                "A,AddToCart,1571756436551,350,false",
                "R,ViewCart.1,1571756442554,509,false,1407,48161,200,https://production-nam-torrid.demandware.net/s/torrid/cart,text/html,0,0,500,7,500,507,,,,,0,,",
                "R,ViewCart.2,1571756443162,75,false,1395,618,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Responsive-IsMobileCustomerGroup,application/json,0,0,75,0,75,75,,,,,0,,",
                "R,ViewCart.3,1571756443248,57,false,1395,618,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Responsive-IsMobileCustomerGroup,application/json,0,0,57,0,57,57,,,,,0,,",
                "R,ViewCart.4,1571756443428,62,false,1859,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fcart&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Torrid-cart&dwac=0.8921267138294733&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fproduct%2Fspecial-occasion-ivory-off-shoulder-sequin-lace-gown%2F11818822.html%23q%3DBlack%2BWhite%2BDress%26prefn1%3Dsize%26prefv1%3D12%26start%3D9&email=xc32436c2a8825%40varmail.de,image/gif,0,0,62,0,62,62,,,,,0,,",
                "A,ViewCart,1571756442554,937,false",
                "R,COEnterCheckout.1,1571756448345,96,false,1444,1572,302,https://production-nam-torrid.demandware.net/s/torrid/cart?dwcont=C150156376,text/html,0,0,93,1,93,94,,,,,0,,",
                "R,COEnterCheckout.2,1571756448440,175,false,1339,17431,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOCustomer-Start,text/html,0,0,174,1,174,175,,,,,0,,",
                "R,COEnterCheckout.3,1571756448626,137,false,1387,3324,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOBilling-UpdateSummary?checkoutstep=3,text/html,0,0,135,1,135,136,,,,,0,,",
                "R,COEnterCheckout.4,1571756448765,121,false,1387,3324,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOBilling-UpdateSummary?checkoutstep=3,text/html,0,0,121,0,121,121,,,,,0,,",
                "R,COEnterCheckout.5,1571756448888,75,false,1435,1762,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Tealium-FooterInclude?pagecontexttype=checkout&pagecontexttitle=Checkout&pagename=billing,text/html,0,0,75,0,75,75,,,,,0,,",
                "R,COEnterCheckout.6,1571756449072,58,false,1829,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FSPCCOCustomer-Start&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.8946933242741676&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fs%2Ftorrid%2Fcart&email=xc32436c2a8825%40varmail.de,image/gif,0,0,58,0,58,58,,,,,0,,",
                "A,COEnterCheckout,1571756448345,786,false",
                "R,COLogin.1,1571756452961,324,false,1626,1248,302,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOCustomer-LoginForm?scope=checkout&format=ajax,text/html,0,0,322,0,322,322,,,,,0,,",
                "R,COLogin.2,1571756453285,367,false,1376,10241,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOShipping-Start?login=true,text/html,0,0,367,0,367,367,,,,,0,,",
                "R,COLogin.3,1571756453661,151,false,1439,3335,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOBilling-UpdateSummary?checkoutstep=3,text/html,0,0,150,1,150,151,,,,,0,,",
                "R,COLogin.4,1571756453907,63,false,1938,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FSPCCOCustomer-Start&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.30678991099340935&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FSPCCOCustomer-Start&email=xc32436c2a8825%40varmail.de,image/gif,0,0,62,0,62,62,,,,,0,,",
                "A,COLogin,1571756452960,1009,false",
                "R,COFillShippingForm.1,1571756457738,195,false,1547,614,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOShipping-SelectShippingMethod?address1=6+Wall+St&address2=&countryCode=US&stateCode=MA&postalCode=01803-4758&city=Burlington&shippingMethodID=3D,application/json,0,0,194,0,194,194,,,,,0,,",
                "R,COFillShippingForm.2,1571756457934,154,false,1439,3338,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOBilling-UpdateSummary?checkoutstep=3,text/html,0,0,152,0,152,152,,,,,0,,",
                "A,COFillShippingForm,1571756457737,352,false",
                "R,COShipping.1,1571756461968,194,false,2522,933,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/VerifyAddress-ShippingAddressSPC,text/html,0,0,193,0,193,193,,,,,0,,",
                "R,COShipping.2,1571756462166,277,false,2599,15100,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOShipping-SingleShipping?format=ajax,text/html,0,0,277,0,277,277,,,,,0,,",
                "R,COShipping.3,1571756462453,131,false,1434,6426,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/COBilling-UpdatePaymentMethodSection,text/html,0,0,131,0,131,131,,,,,0,,",
                "R,COShipping.4,1571756462590,158,false,1424,3581,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOBilling-UpdateSummary,text/html,0,0,158,0,158,158,,,,,0,,",
                "R,COShipping.5,1571756462752,100,false,1437,616,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/COBilling-UpdateGiftCertificatesApplied,text/html,0,0,100,0,100,100,,,,,0,,",
                "R,COShipping.6,1571756462852,70,false,1487,1919,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/Tealium-FooterInclude?pagecontexttype=checkout&pagecontexttitle=Checkout&pagename=billing,text/html,0,0,69,0,69,69,,,,,0,,",
                "R,COShipping.7,1571756462923,109,false,1434,6426,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/COBilling-UpdatePaymentMethodSection,text/html,0,0,109,0,109,109,,,,,0,,",
                "R,COShipping.8,1571756463139,60,false,1937,538,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/__Analytics-Start?url=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FSPCCOCustomer-Start&res=1600x1200&cookie=1&cmpn=&java=0&gears=0&fla=0&ag=0&dir=0&pct=0&pdf=0&qt=0&realp=0&tz=US%2FEastern&wma=1&pcat=new-arrivals&title=Sites-torrid-Site&dwac=0.8231383979796524&r=5205092883373519364&ref=https%3A%2F%2Fproduction-nam-torrid.demandware.net%2Fon%2Fdemandware.store%2FSites-torrid-Site%2Fdefault%2FSPCCOCustomer-Start&email=xc32436c2a8825%40varmail.de,image/gif,0,0,60,0,60,60,,,,,0,,",
                "A,COShipping,1571756461967,1233,false",
                "E,Script at non expected position. Check for defective HTML. Search phrase : 'Payeezy.setApiEndpoint',1571756469245,TCheckout,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/SPCCOCustomer-Start",
                "R,COBilling.1,1571756469247,69,false,1900,1102,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/FirstData-WriteLog?data=https%3A%2F%2Fapi-cert.payeezy.com%2Fv1%2Fsecuritytokens%3Fauth%3Dfalse%26js_security_key%3Djs-546813adc251401f383bc410d3ab57941f8097d6c71c9413%26ta_token%3DNOIW%26apikey%3D3ZQCMQecwpaTDbivoTDb305nDA5x7a4H%26trtoken%3D0c9d1534b5a53801%26callback%3DPayeezy.callback%26type%3DFDToken%26credit_card.type%3DVisa%26credit_card.cardholder_name%3DJohn+Doe%26credit_card.card_number%3D%5BHiddenInLogs%5D%26credit_card.exp_date%3D%5BHiddenInLogs%5D%26credit_card.cvv%3D%5BHiddenInLogs%5D,text/html,0,0,68,0,68,68,,,,,0,,",
                "R,COBilling.2,1571756469317,97,false,1459,5107,200,https://api-cert.payeezy.com/v1/securitytokens?auth=false&js_security_key=js-546813adc251401f383bc410d3ab57941f8097d6c71c9413&ta_token=NOIW&apikey=3ZQCMQecwpaTDbivoTDb305nDA5x7a4H&trtoken=0c9d1534b5a53801&callback=Payeezy.callback&type=FDToken&credit_card.type=Visa&credit_card.cardholder_name=John+Doe&credit_card.card_number=4111111111111111&credit_card.exp_date=0220&credit_card.cvv=123,application/json,1,8,0,92,5,97,,,,,0,,",
                "R,COBilling.3,1571756469415,72,false,1732,1135,200,https://production-nam-torrid.demandware.net/on/demandware.store/Sites-torrid-Site/default/FirstData-WriteLog?data=%7B%22status%22%3A201%2C%22results%22%3A%7B%22correlation_id%22%3A%22124.5717564695019%22%2C%22status%22%3A%22success%22%2C%22type%22%3A%22FDToken%22%2C%22token%22%3A%7B%22type%22%3A%22Visa%22%2C%22cardholder_name%22%3A%22JohnDoe%22%2C%22exp_date%22%3A%220220%22%2C%22value%22%3A%229495846215171111%22%7D%7D%7D,text/html,0,0,72,0,72,72,,,,,0,,",
                "E,Parameter value 'null' was converted into empty String for key,1571756469492,TCheckout,dwfrm_billing_billingAddress_addressFields_address2",
        };
        
        for (int i = 0; i < lines.length; i++)
        {
            clines.add(lines[i].toCharArray());
        }
    }

    @Benchmark
    public int original()
    { 
        int sum = 0;
        for (String s : lines)
        {
            String[] result = com.xceptance.common.csvutils.original411.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }

    @Benchmark
    public int tuned412()
    { 
        int sum = 0;
        for (String s : lines)
        {
            String[] result = com.xceptance.common.csvutils.tuned412.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }

    @Benchmark
    public int tuned4121()
    { 
        int sum = 0;
        for (String s : lines)
        {
            String[] result = com.xceptance.common.csvutils.tuned4121.CsvUtils.decode(s);
            sum += result.length;
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned500()
    { 
        int sum = 0;
        for (String s : lines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned500.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
     
    
    @Benchmark
    public int tuned501()
    { 
        int sum = 0;
        for (String s : lines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtils.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned501Decode()
    { 
        int sum = 0;
        for (String s : lines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned501.CsvUtilsDecode.decode(s);
            sum += result.size();
        }
        
        return sum;
    }
    
    @Benchmark
    public int tuned503()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            List<String> result = com.xceptance.common.csvutils.tuned503_rewrite.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }

    @Benchmark
    public int tuned504()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            SimpleArrayList<String> result = com.xceptance.common.csvutils.tuned504_simplelist.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }

    @Benchmark
    public int tuned505()
    { 
        int sum = 0;
        for (char[] s : clines)
        {
            List<char[]> result = com.xceptance.common.csvutils.tuned505_tuned503.CsvUtilsDecode.parse(s, ',');
            sum += result.size();
        }
        
        return sum;
    }
    
    @Test
    public void verify()
    { 
        for (String s : lines)
        {
            List<String> result1 = com.xceptance.common.csvutils.tuned500.CsvUtils.decode(s);
            List<String> result2 = com.xceptance.common.csvutils.tuned501.CsvUtilsDecode.decode(s);
            
            Assert.assertTrue(result1.equals(result2));
        }
    }
}