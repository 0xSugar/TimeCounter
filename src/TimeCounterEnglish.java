import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author skul
 */

public class TimeCounterEnglish {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException, ParseException {
        System.out.print("Enter your gender (m/w): ");
        boolean sex = checkSEX();

        System.out.print("\nEnter your date of birth (format 16/02/1985): ");
        Calendar dateOfBirth = checkDate();

        System.out.print("\nEnter the country you live (UA / USA / AMS): ");
        String livedCountry = checkCountry();

        HashMap<String, Integer> map = fillHashMap();

        SimpleDateFormat type1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        System.out.println("You were born: " + type1.format(dateOfBirth.getTime()));
        howManyLived(dateOfBirth);
        leftToLive(dateOfBirth, sex, livedCountry, map);
    }

    /** Read sex / date / country and chech it */
    private static boolean checkSEX() throws IOException {
        boolean sex;
        while (true) {
            String sexline = reader.readLine();
            sexline = sexline.toUpperCase();
            if (sexline.charAt(0) == 'M' || sexline.charAt(0) == 'М' || sexline.equals("МУЖСКОЙ") || sexline.equals("МУЖ") || sexline.equals("MAN")) {
                sex = true;
                break;
            } else if (sexline.charAt(0) == 'Ж' || sexline.charAt(0) == 'W' || sexline.equals("ЖЕНСКИЙ") || sexline.equals("ЖЕН") || sexline.equals("WOMAN")) {
                sex = false;
                break;
            } else {
                System.out.print("\nMistake. Enter your gender (man / woman): ");
            }
        }
        return sex;
    }
    private static Calendar checkDate() throws IOException, ParseException {
        String dateOfBirth;
        while (true) {
            dateOfBirth = reader.readLine();
            if (isCorrectDate(dateOfBirth)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateOfBirth);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar;
            }
            System.out.print("\nInvalid format, try again: ");
        }
    }
    private static boolean isCorrectDate(String dateline) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar timeNow = Calendar.getInstance();
        Date date;

        try { // check doesn't allow such values 21424/21412/421/4/124/1 - it thinks like 2142 days, 4 months and 21412 year, other
            String[] checker = dateline.split("/");
            if (checker.length > 3) { // if the line has more than 3 values (15/04/1982/23)
                return false;
            } else if (Integer.parseInt(checker[0]) > 31) {  // if day is 32+
                return false;
            } else if (Integer.parseInt(checker[1]) > 12) { // if month is 13+
                return false;                               // if the year is until 1900 or after 2021 (if now (2020) + 1)
            } else if (!(Integer.parseInt(checker[2]) > 1900 && Integer.parseInt(checker[2]) < (timeNow.get(Calendar.YEAR) + 1))) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        try {
            date = sdf.parse(dateline);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    private static String checkCountry() throws IOException {
        String country;
        while (true) {
            country = reader.readLine();
            country = country.toUpperCase();
            if (country.length() < 4 || country.equals("УКРАИНА") || country.equals("РОССИЯ") || country.equals("UKRAINE") || country.equals("RUSSIA")) {
                // If Ukraine - return UA
                if (country.equals("УКРАИНА") || country.equals("UKRAINE")) {
                    return "UA";
                }
                // If Russia - return RU
                if (country.equals("РОССИЯ") || country.equals("RUSSIA")) {
                    return "RU";
                }
                // if the country has 3 letters - return this country in 2 symbols
                if (country.length() == 3 && (country.equals("РУС") || country.equals("УКР") || country.equals("UKR") || country.equals("AFG") || country.equals("ALB") || country.equals("DZA") || country.equals("ASM") || country.equals("AND") || country.equals("AGO") || country.equals("AIA") || country.equals("ATA") || country.equals("ATG") || country.equals("ARG") || country.equals("ARM") || country.equals("ABW") || country.equals("AUS") || country.equals("AUT") || country.equals("AZE") || country.equals("BHS") || country.equals("BHR") || country.equals("BGD") || country.equals("BRB") || country.equals("BLR") || country.equals("BEL") || country.equals("BLZ") || country.equals("BEN") || country.equals("BMU") || country.equals("BTN") || country.equals("BOL") || country.equals("BIH") || country.equals("BWA") || country.equals("BRA") || country.equals("IOT") || country.equals("VGB") || country.equals("BRN") || country.equals("BGR") || country.equals("BFA") || country.equals("BDI") || country.equals("KHM") || country.equals("CMR") || country.equals("CAN") || country.equals("CPV") || country.equals("CYM") || country.equals("CAF") || country.equals("TCD") || country.equals("CHL") || country.equals("CHN") || country.equals("CXR") || country.equals("CCK") || country.equals("COL") || country.equals("COM") || country.equals("COK") || country.equals("CRI") || country.equals("HRV") || country.equals("CUB") || country.equals("CUW") || country.equals("CYP") || country.equals("CZE") || country.equals("COD") || country.equals("DNK") || country.equals("DJI") || country.equals("DMA") || country.equals("DOM") || country.equals("TLS") || country.equals("ECU") || country.equals("EGY") || country.equals("SLV") || country.equals("GNQ") || country.equals("ERI") || country.equals("EST") || country.equals("ETH") || country.equals("FLK") || country.equals("FRO") || country.equals("FJI") || country.equals("FIN") || country.equals("FRA") || country.equals("PYF") || country.equals("GAB") || country.equals("GMB") || country.equals("GEO") || country.equals("DEU") || country.equals("GHA") || country.equals("GIB") || country.equals("GRC") || country.equals("GRL") || country.equals("GRD") || country.equals("GUM") || country.equals("GTM") || country.equals("GGY") || country.equals("GIN") || country.equals("GNB") || country.equals("GUY") || country.equals("HTI") || country.equals("HND") || country.equals("HKG") || country.equals("HUN") || country.equals("ISL") || country.equals("IND") || country.equals("IDN") || country.equals("IRN") || country.equals("IRQ") || country.equals("IRL") || country.equals("IMN") || country.equals("ISR") || country.equals("ITA") || country.equals("CIV") || country.equals("JAM") || country.equals("JPN") || country.equals("JEY") || country.equals("JOR") || country.equals("KAZ") || country.equals("KEN") || country.equals("KIR") || country.equals("XKX") || country.equals("KWT") || country.equals("KGZ") || country.equals("LAO") || country.equals("LVA") || country.equals("LBN") || country.equals("LSO") || country.equals("LBR") || country.equals("LBY") || country.equals("LIE") || country.equals("LTU") || country.equals("LUX") || country.equals("MAC") || country.equals("MKD") || country.equals("MDG") || country.equals("MWI") || country.equals("MYS") || country.equals("MDV") || country.equals("MLI") || country.equals("MLT") || country.equals("MHL") || country.equals("MRT") || country.equals("MUS") || country.equals("MYT") || country.equals("MEX") || country.equals("FSM") || country.equals("MDA") || country.equals("MCO") || country.equals("MNG") || country.equals("MNE") || country.equals("MSR") || country.equals("MAR") || country.equals("MOZ") || country.equals("MMR") || country.equals("NAM") || country.equals("NRU") || country.equals("NPL") || country.equals("NLD") || country.equals("ANT") || country.equals("NCL") || country.equals("NZL") || country.equals("NIC") || country.equals("NER") || country.equals("NGA") || country.equals("NIU") || country.equals("PRK") || country.equals("MNP") || country.equals("NOR") || country.equals("OMN") || country.equals("PAK") || country.equals("PLW") || country.equals("PSE") || country.equals("PAN") || country.equals("PNG") || country.equals("PRY") || country.equals("PER") || country.equals("PHL") || country.equals("PCN") || country.equals("POL") || country.equals("PRT") || country.equals("PRI") || country.equals("QAT") || country.equals("COG") || country.equals("REU") || country.equals("ROU") || country.equals("RUS") || country.equals("RWA") || country.equals("BLM") || country.equals("SHN") || country.equals("KNA") || country.equals("LCA") || country.equals("MAF") || country.equals("SPM") || country.equals("VCT") || country.equals("WSM") || country.equals("SMR") || country.equals("STP") || country.equals("SAU") || country.equals("SEN") || country.equals("SRB") || country.equals("SYC") || country.equals("SLE") || country.equals("SGP") || country.equals("SXM") || country.equals("SVK") || country.equals("SVN") || country.equals("SLB") || country.equals("SOM") || country.equals("ZAF") || country.equals("KOR") || country.equals("SSD") || country.equals("ESP") || country.equals("LKA") || country.equals("SDN") || country.equals("SUR") || country.equals("SJM") || country.equals("SWZ") || country.equals("SWE") || country.equals("CHE") || country.equals("SYR") || country.equals("TWN") || country.equals("TJK") || country.equals("TZA") || country.equals("THA") || country.equals("TGO") || country.equals("TKL") || country.equals("TON") || country.equals("TTO") || country.equals("TUN") || country.equals("TUR") || country.equals("TKM") || country.equals("TCA") || country.equals("TUV") || country.equals("VIR") || country.equals("UGA") || country.equals("ARE") || country.equals("GBR") || country.equals("USA") || country.equals("URY") || country.equals("UZB") || country.equals("VUT") || country.equals("VAT") || country.equals("VEN") || country.equals("VNM") || country.equals("WLF") || country.equals("ESH") || country.equals("YEM") || country.equals("ZMB") || country.equals("ZWE"))) {
                    if (country.equals("РУС")) return "RU";
                    if (country.equals("RUS")) return "RU";
                    if (country.equals("УКР")) return "UA";
                    if (country.equals("UKR")) return "UA";
                    if (country.equals("AFG")) return "AF";
                    if (country.equals("ALB")) return "AL";
                    if (country.equals("DZA")) return "DZ";
                    if (country.equals("ASM")) return "AS";
                    if (country.equals("AND")) return "AD";
                    if (country.equals("AGO")) return "AO";
                    if (country.equals("AIA")) return "AI";
                    if (country.equals("ATA")) return "AQ";
                    if (country.equals("ATG")) return "AG";
                    if (country.equals("ARG")) return "AR";
                    if (country.equals("ARM")) return "AM";
                    if (country.equals("ABW")) return "AW";
                    if (country.equals("AUS")) return "AU";
                    if (country.equals("AUT")) return "AT";
                    if (country.equals("AZE")) return "AZ";
                    if (country.equals("BHS")) return "BS";
                    if (country.equals("BHR")) return "BH";
                    if (country.equals("BGD")) return "BD";
                    if (country.equals("BRB")) return "BB";
                    if (country.equals("BLR")) return "BY";
                    if (country.equals("BEL")) return "BE";
                    if (country.equals("BLZ")) return "BZ";
                    if (country.equals("BEN")) return "BJ";
                    if (country.equals("BMU")) return "BM";
                    if (country.equals("BTN")) return "BT";
                    if (country.equals("BOL")) return "BO";
                    if (country.equals("BIH")) return "BA";
                    if (country.equals("BWA")) return "BW";
                    if (country.equals("BRA")) return "BR";
                    if (country.equals("IOT")) return "IO";
                    if (country.equals("VGB")) return "VG";
                    if (country.equals("BRN")) return "BN";
                    if (country.equals("BGR")) return "BG";
                    if (country.equals("BFA")) return "BF";
                    if (country.equals("BDI")) return "BI";
                    if (country.equals("KHM")) return "KH";
                    if (country.equals("CMR")) return "CM";
                    if (country.equals("CAN")) return "CA";
                    if (country.equals("CPV")) return "CV";
                    if (country.equals("CYM")) return "KY";
                    if (country.equals("CAF")) return "CF";
                    if (country.equals("TCD")) return "TD";
                    if (country.equals("CHL")) return "CL";
                    if (country.equals("CHN")) return "CN";
                    if (country.equals("CXR")) return "CX";
                    if (country.equals("CCK")) return "CC";
                    if (country.equals("COL")) return "CO";
                    if (country.equals("COM")) return "KM";
                    if (country.equals("COK")) return "CK";
                    if (country.equals("CRI")) return "CR";
                    if (country.equals("HRV")) return "HR";
                    if (country.equals("CUB")) return "CU";
                    if (country.equals("CUW")) return "CW";
                    if (country.equals("CYP")) return "CY";
                    if (country.equals("CZE")) return "CZ";
                    if (country.equals("COD")) return "CD";
                    if (country.equals("DNK")) return "DK";
                    if (country.equals("DJI")) return "DJ";
                    if (country.equals("DMA")) return "DM";
                    if (country.equals("DOM")) return "DO";
                    if (country.equals("TLS")) return "TL";
                    if (country.equals("ECU")) return "EC";
                    if (country.equals("EGY")) return "EG";
                    if (country.equals("SLV")) return "SV";
                    if (country.equals("GNQ")) return "GQ";
                    if (country.equals("ERI")) return "ER";
                    if (country.equals("EST")) return "EE";
                    if (country.equals("ETH")) return "ET";
                    if (country.equals("FLK")) return "FK";
                    if (country.equals("FRO")) return "FO";
                    if (country.equals("FJI")) return "FJ";
                    if (country.equals("FIN")) return "FI";
                    if (country.equals("FRA")) return "FR";
                    if (country.equals("PYF")) return "PF";
                    if (country.equals("GAB")) return "GA";
                    if (country.equals("GMB")) return "GM";
                    if (country.equals("GEO")) return "GE";
                    if (country.equals("DEU")) return "DE";
                    if (country.equals("GHA")) return "GH";
                    if (country.equals("GIB")) return "GI";
                    if (country.equals("GRC")) return "GR";
                    if (country.equals("GRL")) return "GL";
                    if (country.equals("GRD")) return "GD";
                    if (country.equals("GUM")) return "GU";
                    if (country.equals("GTM")) return "GT";
                    if (country.equals("GGY")) return "GG";
                    if (country.equals("GIN")) return "GN";
                    if (country.equals("GNB")) return "GW";
                    if (country.equals("GUY")) return "GY";
                    if (country.equals("HTI")) return "HT";
                    if (country.equals("HND")) return "HN";
                    if (country.equals("HKG")) return "HK";
                    if (country.equals("HUN")) return "HU";
                    if (country.equals("ISL")) return "IS";
                    if (country.equals("IND")) return "IN";
                    if (country.equals("IDN")) return "ID";
                    if (country.equals("IRN")) return "IR";
                    if (country.equals("IRQ")) return "IQ";
                    if (country.equals("IRL")) return "IE";
                    if (country.equals("IMN")) return "IM";
                    if (country.equals("ISR")) return "IL";
                    if (country.equals("ITA")) return "IT";
                    if (country.equals("CIV")) return "CI";
                    if (country.equals("JAM")) return "JM";
                    if (country.equals("JPN")) return "JP";
                    if (country.equals("JEY")) return "JE";
                    if (country.equals("JOR")) return "JO";
                    if (country.equals("KAZ")) return "KZ";
                    if (country.equals("KEN")) return "KE";
                    if (country.equals("KIR")) return "KI";
                    if (country.equals("XKX")) return "XK";
                    if (country.equals("KWT")) return "KW";
                    if (country.equals("KGZ")) return "KG";
                    if (country.equals("LAO")) return "LA";
                    if (country.equals("LVA")) return "LV";
                    if (country.equals("LBN")) return "LB";
                    if (country.equals("LSO")) return "LS";
                    if (country.equals("LBR")) return "LR";
                    if (country.equals("LBY")) return "LY";
                    if (country.equals("LIE")) return "LI";
                    if (country.equals("LTU")) return "LT";
                    if (country.equals("LUX")) return "LU";
                    if (country.equals("MAC")) return "MO";
                    if (country.equals("MKD")) return "MK";
                    if (country.equals("MDG")) return "MG";
                    if (country.equals("MWI")) return "MW";
                    if (country.equals("MYS")) return "MY";
                    if (country.equals("MDV")) return "MV";
                    if (country.equals("MLI")) return "ML";
                    if (country.equals("MLT")) return "MT";
                    if (country.equals("MHL")) return "MH";
                    if (country.equals("MRT")) return "MR";
                    if (country.equals("MUS")) return "MU";
                    if (country.equals("MYT")) return "YT";
                    if (country.equals("MEX")) return "MX";
                    if (country.equals("FSM")) return "FM";
                    if (country.equals("MDA")) return "MD";
                    if (country.equals("MCO")) return "MC";
                    if (country.equals("MNG")) return "MN";
                    if (country.equals("MNE")) return "ME";
                    if (country.equals("MSR")) return "MS";
                    if (country.equals("MAR")) return "MA";
                    if (country.equals("MOZ")) return "MZ";
                    if (country.equals("MMR")) return "MM";
                    if (country.equals("NAM")) return "NA";
                    if (country.equals("NRU")) return "NR";
                    if (country.equals("NPL")) return "NP";
                    if (country.equals("NLD")) return "NL";
                    if (country.equals("ANT")) return "AN";
                    if (country.equals("NCL")) return "NC";
                    if (country.equals("NZL")) return "NZ";
                    if (country.equals("NIC")) return "NI";
                    if (country.equals("NER")) return "NE";
                    if (country.equals("NGA")) return "NG";
                    if (country.equals("NIU")) return "NU";
                    if (country.equals("PRK")) return "KP";
                    if (country.equals("MNP")) return "MP";
                    if (country.equals("NOR")) return "NO";
                    if (country.equals("OMN")) return "OM";
                    if (country.equals("PAK")) return "PK";
                    if (country.equals("PLW")) return "PW";
                    if (country.equals("PSE")) return "PS";
                    if (country.equals("PAN")) return "PA";
                    if (country.equals("PNG")) return "PG";
                    if (country.equals("PRY")) return "PY";
                    if (country.equals("PER")) return "PE";
                    if (country.equals("PHL")) return "PH";
                    if (country.equals("PCN")) return "PN";
                    if (country.equals("POL")) return "PL";
                    if (country.equals("PRT")) return "PT";
                    if (country.equals("PRI")) return "PR";
                    if (country.equals("QAT")) return "QA";
                    if (country.equals("COG")) return "CG";
                    if (country.equals("REU")) return "RE";
                    if (country.equals("ROU")) return "RO";
                    if (country.equals("RWA")) return "RW";
                    if (country.equals("BLM")) return "BL";
                    if (country.equals("SHN")) return "SH";
                    if (country.equals("KNA")) return "KN";
                    if (country.equals("LCA")) return "LC";
                    if (country.equals("MAF")) return "MF";
                    if (country.equals("SPM")) return "PM";
                    if (country.equals("VCT")) return "VC";
                    if (country.equals("WSM")) return "WS";
                    if (country.equals("SMR")) return "SM";
                    if (country.equals("STP")) return "ST";
                    if (country.equals("SAU")) return "SA";
                    if (country.equals("SEN")) return "SN";
                    if (country.equals("SRB")) return "RS";
                    if (country.equals("SYC")) return "SC";
                    if (country.equals("SLE")) return "SL";
                    if (country.equals("SGP")) return "SG";
                    if (country.equals("SXM")) return "SX";
                    if (country.equals("SVK")) return "SK";
                    if (country.equals("SVN")) return "SI";
                    if (country.equals("SLB")) return "SB";
                    if (country.equals("SOM")) return "SO";
                    if (country.equals("ZAF")) return "ZA";
                    if (country.equals("KOR")) return "KR";
                    if (country.equals("SSD")) return "SS";
                    if (country.equals("ESP")) return "ES";
                    if (country.equals("LKA")) return "LK";
                    if (country.equals("SDN")) return "SD";
                    if (country.equals("SUR")) return "SR";
                    if (country.equals("SJM")) return "SJ";
                    if (country.equals("SWZ")) return "SZ";
                    if (country.equals("SWE")) return "SE";
                    if (country.equals("CHE")) return "CH";
                    if (country.equals("SYR")) return "SY";
                    if (country.equals("TWN")) return "TW";
                    if (country.equals("TJK")) return "TJ";
                    if (country.equals("TZA")) return "TZ";
                    if (country.equals("THA")) return "TH";
                    if (country.equals("TGO")) return "TG";
                    if (country.equals("TKL")) return "TK";
                    if (country.equals("TON")) return "TO";
                    if (country.equals("TTO")) return "TT";
                    if (country.equals("TUN")) return "TN";
                    if (country.equals("TUR")) return "TR";
                    if (country.equals("TKM")) return "TM";
                    if (country.equals("TCA")) return "TC";
                    if (country.equals("TUV")) return "TV";
                    if (country.equals("VIR")) return "VI";
                    if (country.equals("UGA")) return "UG";
                    if (country.equals("ARE")) return "AE";
                    if (country.equals("GBR")) return "GB";
                    if (country.equals("USA")) return "US";
                    if (country.equals("URY")) return "UY";
                    if (country.equals("UZB")) return "UZ";
                    if (country.equals("VUT")) return "VU";
                    if (country.equals("VAT")) return "VA";
                    if (country.equals("VEN")) return "VE";
                    if (country.equals("VNM")) return "VN";
                    if (country.equals("WLF")) return "WF";
                    if (country.equals("ESH")) return "EH";
                    if (country.equals("YEM")) return "YE";
                    if (country.equals("ZMB")) return "ZM";
                    if (country.equals("ZWE")) return "ZW";
                }
                // if the country has 2 letters - return this country
                if (country.length() == 2 && (country.equals("AF") || country.equals("AL") || country.equals("DZ") || country.equals("AS") || country.equals("AD") || country.equals("AO") || country.equals("AI") || country.equals("AQ") || country.equals("AG") || country.equals("AR") || country.equals("AM") || country.equals("AW") || country.equals("AU") || country.equals("AT") || country.equals("AZ") || country.equals("BS") || country.equals("BH") || country.equals("BD") || country.equals("BB") || country.equals("BY") || country.equals("BE") || country.equals("BZ") || country.equals("BJ") || country.equals("BM") || country.equals("BT") || country.equals("BO") || country.equals("BA") || country.equals("BW") || country.equals("BR") || country.equals("IO") || country.equals("VG") || country.equals("BN") || country.equals("BG") || country.equals("BF") || country.equals("BI") || country.equals("KH") || country.equals("CM") || country.equals("CA") || country.equals("CV") || country.equals("KY") || country.equals("CF") || country.equals("TD") || country.equals("CL") || country.equals("CN") || country.equals("CX") || country.equals("CC") || country.equals("CO") || country.equals("KM") || country.equals("CK") || country.equals("CR") || country.equals("HR") || country.equals("CU") || country.equals("CW") || country.equals("CY") || country.equals("CZ") || country.equals("CD") || country.equals("DK") || country.equals("DJ") || country.equals("DM") || country.equals("DO") || country.equals("TL") || country.equals("EC") || country.equals("EG") || country.equals("SV") || country.equals("GQ") || country.equals("ER") || country.equals("EE") || country.equals("ET") || country.equals("FK") || country.equals("FO") || country.equals("FJ") || country.equals("FI") || country.equals("FR") || country.equals("PF") || country.equals("GA") || country.equals("GM") || country.equals("GE") || country.equals("DE") || country.equals("GH") || country.equals("GI") || country.equals("GR") || country.equals("GL") || country.equals("GD") || country.equals("GU") || country.equals("GT") || country.equals("GG") || country.equals("GN") || country.equals("GW") || country.equals("GY") || country.equals("HT") || country.equals("HN") || country.equals("HK") || country.equals("HU") || country.equals("IS") || country.equals("IN") || country.equals("ID") || country.equals("IR") || country.equals("IQ") || country.equals("IE") || country.equals("IM") || country.equals("IL") || country.equals("IT") || country.equals("CI") || country.equals("JM") || country.equals("JP") || country.equals("JE") || country.equals("JO") || country.equals("KZ") || country.equals("KE") || country.equals("KI") || country.equals("XK") || country.equals("KW") || country.equals("KG") || country.equals("LA") || country.equals("LV") || country.equals("LB") || country.equals("LS") || country.equals("LR") || country.equals("LY") || country.equals("LI") || country.equals("LT") || country.equals("LU") || country.equals("MO") || country.equals("MK") || country.equals("MG") || country.equals("MW") || country.equals("MY") || country.equals("MV") || country.equals("ML") || country.equals("MT") || country.equals("MH") || country.equals("MR") || country.equals("MU") || country.equals("YT") || country.equals("MX") || country.equals("FM") || country.equals("MD") || country.equals("MC") || country.equals("MN") || country.equals("ME") || country.equals("MS") || country.equals("MA") || country.equals("MZ") || country.equals("MM") || country.equals("NA") || country.equals("NR") || country.equals("NP") || country.equals("NL") || country.equals("AN") || country.equals("NC") || country.equals("NZ") || country.equals("NI") || country.equals("NE") || country.equals("NG") || country.equals("NU") || country.equals("KP") || country.equals("MP") || country.equals("NO") || country.equals("OM") || country.equals("PK") || country.equals("PW") || country.equals("PS") || country.equals("PA") || country.equals("PG") || country.equals("PY") || country.equals("PE") || country.equals("PH") || country.equals("PN") || country.equals("PL") || country.equals("PT") || country.equals("PR") || country.equals("QA") || country.equals("CG") || country.equals("RE") || country.equals("RO") || country.equals("RU") || country.equals("RW") || country.equals("BL") || country.equals("SH") || country.equals("KN") || country.equals("LC") || country.equals("MF") || country.equals("PM") || country.equals("VC") || country.equals("WS") || country.equals("SM") || country.equals("ST") || country.equals("SA") || country.equals("SN") || country.equals("RS") || country.equals("SC") || country.equals("SL") || country.equals("SG") || country.equals("SX") || country.equals("SK") || country.equals("SI") || country.equals("SB") || country.equals("SO") || country.equals("ZA") || country.equals("KR") || country.equals("SS") || country.equals("ES") || country.equals("LK") || country.equals("SD") || country.equals("SR") || country.equals("SJ") || country.equals("SZ") || country.equals("SE") || country.equals("CH") || country.equals("SY") || country.equals("TW") || country.equals("TJ") || country.equals("TZ") || country.equals("TH") || country.equals("TG") || country.equals("TK") || country.equals("TO") || country.equals("TT") || country.equals("TN") || country.equals("TR") || country.equals("TM") || country.equals("TC") || country.equals("TV") || country.equals("VI") || country.equals("UG") || country.equals("UA") || country.equals("AE") || country.equals("GB") || country.equals("US") || country.equals("UY") || country.equals("UZ") || country.equals("VU") || country.equals("VA") || country.equals("VE") || country.equals("VN") || country.equals("WF") || country.equals("EH") || country.equals("YE") || country.equals("ZM") || country.equals("ZW") || country.equals("ЮА") || country.equals("РУ"))) {
                    return country;
                }
                System.out.print("\nInvalid format of the country, try again: ");
            } else {
                System.out.print("\nInvalid format of the country, try again: ");
            }
        }
    }

    /** Fills HashMap with longevity of life over countries */
    private static HashMap<String, Integer> fillHashMap() {
        HashMap<String, Integer> map = new HashMap();
        map.put("AF_M", 63);
        map.put("AF_W", 66);
        map.put("AL_M", 72);
        map.put("AL_W", 78);
        map.put("DZ_M", 75);
        map.put("DZ_W", 78);
        map.put("AS_M", 71);
        map.put("AS_W", 76);
        map.put("AD_M", 82);
        map.put("AD_W", 82);
        map.put("AO_M", 58);
        map.put("AO_W", 64);
        map.put("AI_M", 79);
        map.put("AI_W", 84);
        map.put("AQ_M", 60);
        map.put("AQ_W", 60);
        map.put("AG_M", 76);
        map.put("AG_W", 78);
        map.put("AR_M", 73);
        map.put("AR_W", 80);
        map.put("AM_M", 71);
        map.put("AM_W", 78);
        map.put("AW_M", 74);
        map.put("AW_W", 80);
        map.put("AU_M", 81);
        map.put("AU_W", 85);
        map.put("AT_M", 79);
        map.put("AT_W", 84);
        map.put("AZ_M", 70);
        map.put("AZ_W", 75);
        map.put("BS_M", 71);
        map.put("BS_W", 76);
        map.put("BH_M", 76);
        map.put("BH_W", 78);
        map.put("BD_M", 71);
        map.put("BD_W", 74);
        map.put("BB_M", 78);
        map.put("BB_W", 80);
        map.put("BY_M", 69);
        map.put("BY_W", 79);
        map.put("BE_M", 79);
        map.put("BE_W", 84);
        map.put("BZ_M", 72);
        map.put("BZ_W", 78);
        map.put("BJ_M", 60);
        map.put("BJ_W", 63);
        map.put("BM_M", 78);
        map.put("BM_W", 85);
        map.put("BT_M", 71);
        map.put("BT_W", 72);
        map.put("BO_M", 68);
        map.put("BO_W", 74);
        map.put("BA_M", 75);
        map.put("BA_W", 80);
        map.put("BW_M", 66);
        map.put("BW_W", 72);
        map.put("BR_M", 72);
        map.put("BR_W", 79);
        map.put("IO_M", 74);
        map.put("IO_W", 80);
        map.put("VG_M", 74);
        map.put("VG_W", 80);
        map.put("BN_M", 75);
        map.put("BN_W", 77);
        map.put("BG_M", 71);
        map.put("BG_W", 78);
        map.put("BF_M", 60);
        map.put("BF_W", 62);
        map.put("BI_M", 59);
        map.put("BI_W", 63);
        map.put("KH_M", 67);
        map.put("KH_W", 72);
        map.put("CM_M", 58);
        map.put("CM_W", 60);
        map.put("CA_M", 80);
        map.put("CA_W", 84);
        map.put("CV_M", 69);
        map.put("CV_W", 76);
        map.put("KY_M", 79);
        map.put("KY_W", 84);
        map.put("CF_M", 51);
        map.put("CF_W", 55);
        map.put("TD_M", 53);
        map.put("TD_W", 55);
        map.put("CL_M", 78);
        map.put("CL_W", 82);
        map.put("CN_M", 73);
        map.put("CN_W", 79);
        map.put("CX_M", 60);
        map.put("CX_W", 60);
        map.put("CC_M", 60);
        map.put("CC_W", 60);
        map.put("CO_M", 74);
        map.put("CO_W", 80);
        map.put("KM_M", 62);
        map.put("KM_W", 66);
        map.put("CK_M", 73);
        map.put("CK_W", 79);
        map.put("CR_M", 77);
        map.put("CR_W", 83);
        map.put("HR_M", 75);
        map.put("HR_W", 81);
        map.put("CU_M", 77);
        map.put("CU_W", 81);
        map.put("CW_M", 76);
        map.put("CW_W", 81);
        map.put("CY_M", 79);
        map.put("CY_W", 83);
        map.put("CZ_M", 77);
        map.put("CZ_W", 82);
        map.put("CD_M", 59);
        map.put("CD_W", 62);
        map.put("DK_M", 78);
        map.put("DK_W", 83);
        map.put("DJ_M", 65);
        map.put("DJ_W", 69);
        map.put("DM_M", 70);
        map.put("DM_W", 78);
        map.put("DO_M", 71);
        map.put("DO_W", 77);
        map.put("TL_M", 67);
        map.put("TL_W", 71);
        map.put("EC_M", 74);
        map.put("EC_W", 80);
        map.put("EG_M", 70);
        map.put("EG_W", 74);
        map.put("SV_M", 68);
        map.put("SV_W", 78);
        map.put("GQ_M", 57);
        map.put("GQ_W", 60);
        map.put("ER_M", 64);
        map.put("ER_W", 68);
        map.put("EE_M", 71);
        map.put("EE_W", 81);
        map.put("ET_M", 64);
        map.put("ET_W", 68);
        map.put("FK_M", 60);
        map.put("FK_W", 60);
        map.put("FO_M", 78);
        map.put("FO_W", 83);
        map.put("FJ_M", 66);
        map.put("FJ_W", 69);
        map.put("FI_M", 79);
        map.put("FI_W", 85);
        map.put("FR_M", 80);
        map.put("FR_W", 85);
        map.put("PF_M", 75);
        map.put("PF_W", 80);
        map.put("GA_M", 64);
        map.put("GA_W", 69);
        map.put("GM_M", 60);
        map.put("GM_W", 63);
        map.put("GE_M", 69);
        map.put("GE_W", 78);
        map.put("DE_M", 79);
        map.put("DE_W", 84);
        map.put("GH_M", 63);
        map.put("GH_W", 65);
        map.put("GI_M", 77);
        map.put("GI_W", 83);
        map.put("GR_M", 80);
        map.put("GR_W", 84);
        map.put("GL_M", 70);
        map.put("GL_W", 75);
        map.put("GD_M", 70);
        map.put("GD_W", 75);
        map.put("GU_M", 74);
        map.put("GU_W", 79);
        map.put("GT_M", 71);
        map.put("GT_W", 77);
        map.put("GG_M", 80);
        map.put("GG_W", 85);
        map.put("GN_M", 60);
        map.put("GN_W", 62);
        map.put("GW_M", 56);
        map.put("GW_W", 60);
        map.put("GY_M", 67);
        map.put("GY_W", 73);
        map.put("HT_M", 61);
        map.put("HT_W", 66);
        map.put("HN_M", 73);
        map.put("HN_W", 77);
        map.put("HK_M", 80);
        map.put("HK_W", 86);
        map.put("HU_M", 73);
        map.put("HU_W", 80);
        map.put("IS_M", 81);
        map.put("IS_W", 84);
        map.put("IN_M", 68);
        map.put("IN_W", 71);
        map.put("ID_M", 69);
        map.put("ID_W", 74);
        map.put("IR_M", 75);
        map.put("IR_W", 78);
        map.put("IQ_M", 68);
        map.put("IQ_W", 72);
        map.put("IE_M", 80);
        map.put("IE_W", 84);
        map.put("IM_M", 80);
        map.put("IM_W", 83);
        map.put("IL_M", 81);
        map.put("IL_W", 84);
        map.put("IT_M", 81);
        map.put("IT_W", 85);
        map.put("CI_M", 56);
        map.put("CI_W", 59);
        map.put("JM_M", 73);
        map.put("JM_W", 76);
        map.put("JP_M", 81);
        map.put("JP_W", 87);
        map.put("JE_M", 79);
        map.put("JE_W", 85);
        map.put("JO_M", 73);
        map.put("JO_W", 76);
        map.put("KZ_M", 69);
        map.put("KZ_W", 77);
        map.put("KE_M", 64);
        map.put("KE_W", 69);
        map.put("KI_M", 64);
        map.put("KI_W", 72);
        map.put("XK_M", 60);
        map.put("XK_W", 60);
        map.put("KW_M", 75);
        map.put("KW_W", 76);
        map.put("KG_M", 67);
        map.put("KG_W", 75);
        map.put("LA_M", 66);
        map.put("LA_W", 69);
        map.put("LV_M", 70);
        map.put("LV_W", 80);
        map.put("LB_M", 77);
        map.put("LB_W", 81);
        map.put("LS_M", 50);
        map.put("LS_W", 57);
        map.put("LR_M", 62);
        map.put("LR_W", 65);
        map.put("LY_M", 70);
        map.put("LY_W", 76);
        map.put("LI_M", 80);
        map.put("LI_W", 80);
        map.put("LT_M", 70);
        map.put("LT_W", 81);
        map.put("LU_M", 80);
        map.put("LU_W", 84);
        map.put("MO_M", 82);
        map.put("MO_W", 88);
        map.put("MK_M", 74);
        map.put("MK_W", 78);
        map.put("MG_M", 65);
        map.put("MG_W", 68);
        map.put("MW_M", 61);
        map.put("MW_W", 67);
        map.put("MY_M", 74);
        map.put("MY_W", 78);
        map.put("MV_M", 77);
        map.put("MV_W", 80);
        map.put("ML_M", 58);
        map.put("ML_W", 60);
        map.put("MT_M", 80);
        map.put("MT_W", 84);
        map.put("MH_M", 74);
        map.put("MH_W", 74);
        map.put("MR_M", 63);
        map.put("MR_W", 66);
        map.put("MU_M", 71);
        map.put("MU_W", 78);
        map.put("YT_M", 76);
        map.put("YT_W", 83);
        map.put("MX_M", 72);
        map.put("MX_W", 77);
        map.put("FM_M", 66);
        map.put("FM_W", 69);
        map.put("MD_M", 67);
        map.put("MD_W", 76);
        map.put("MC_M", 86);
        map.put("MC_W", 93);
        map.put("MN_M", 66);
        map.put("MN_W", 74);
        map.put("ME_M", 74);
        map.put("ME_W", 79);
        map.put("MS_M", 76);
        map.put("MS_W", 73);
        map.put("MA_M", 75);
        map.put("MA_W", 78);
        map.put("MZ_M", 57);
        map.put("MZ_W", 63);
        map.put("MM_M", 64);
        map.put("MM_W", 70);
        map.put("NA_M", 60);
        map.put("NA_W", 66);
        map.put("NR_M", 63);
        map.put("NR_W", 71);
        map.put("NP_M", 69);
        map.put("NP_W", 72);
        map.put("NL_M", 80);
        map.put("NL_W", 84);
        map.put("AN_M", 80);
        map.put("AN_W", 84);
        map.put("NC_M", 74);
        map.put("NC_W", 82);
        map.put("NZ_M", 80);
        map.put("NZ_W", 84);
        map.put("NI_M", 71);
        map.put("NI_W", 78);
        map.put("NE_M", 61);
        map.put("NE_W", 63);
        map.put("NG_M", 53);
        map.put("NG_W", 55);
        map.put("NU_M", 60);
        map.put("NU_W", 60);
        map.put("KP_M", 68);
        map.put("KP_W", 73);
        map.put("MP_M", 73);
        map.put("MP_W", 79);
        map.put("NO_M", 80);
        map.put("NO_W", 84);
        map.put("OM_M", 76);
        map.put("OM_W", 80);
        map.put("PK_M", 66);
        map.put("PK_W", 68);
        map.put("PW_M", 74);
        map.put("PW_W", 74);
        map.put("PS_M", 72);
        map.put("PS_W", 76);
        map.put("PA_M", 82);
        map.put("PA_W", 75);
        map.put("PG_M", 63);
        map.put("PG_W", 66);
        map.put("PY_M", 72);
        map.put("PY_W", 76);
        map.put("PE_M", 74);
        map.put("PE_W", 79);
        map.put("PH_M", 67);
        map.put("PH_W", 75);
        map.put("PN_M", 60);
        map.put("PN_W", 60);
        map.put("PL_M", 75);
        map.put("PL_W", 82);
        map.put("PT_M", 79);
        map.put("PT_W", 85);
        map.put("PR_M", 77);
        map.put("PR_W", 84);
        map.put("QA_M", 79);
        map.put("QA_W", 82);
        map.put("CG_M", 64);
        map.put("CG_W", 66);
        map.put("RE_M", 76);
        map.put("RE_W", 83);
        map.put("RO_M", 72);
        map.put("RO_W", 79);
        map.put("RU_M", 70);
        map.put("RU_W", 75);
        map.put("RW_M", 66);
        map.put("RW_W", 71);
        map.put("BL_M", 60);
        map.put("BL_W", 60);
        map.put("SH_M", 77);
        map.put("SH_W", 83);
        map.put("KN_M", 75);
        map.put("KN_W", 75);
        map.put("LC_M", 75);
        map.put("LC_W", 77);
        map.put("MF_M", 60);
        map.put("MF_W", 60);
        map.put("PM_M", 78);
        map.put("PM_W", 83);
        map.put("VC_M", 70);
        map.put("VC_W", 75);
        map.put("WS_M", 71);
        map.put("WS_W", 75);
        map.put("SM_M", 81);
        map.put("SM_W", 86);
        map.put("ST_M", 68);
        map.put("ST_W", 73);
        map.put("SA_M", 74);
        map.put("SA_W", 77);
        map.put("SN_M", 65);
        map.put("SN_W", 70);
        map.put("RS_M", 73);
        map.put("RS_W", 78);
        map.put("SC_M", 70);
        map.put("SC_W", 77);
        map.put("SL_M", 53);
        map.put("SL_W", 55);
        map.put("SG_M", 81);
        map.put("SG_W", 86);
        map.put("SX_M", 76);
        map.put("SX_W", 81);
        map.put("SK_M", 74);
        map.put("SK_W", 81);
        map.put("SI_M", 78);
        map.put("SI_W", 84);
        map.put("SB_M", 71);
        map.put("SB_W", 75);
        map.put("SO_M", 55);
        map.put("SO_W", 57);
        map.put("ZA_M", 60);
        map.put("ZA_W", 67);
        map.put("KR_M", 80);
        map.put("KR_W", 86);
        map.put("SS_M", 56);
        map.put("SS_W", 59);
        map.put("ES_M", 81);
        map.put("ES_W", 86);
        map.put("LK_M", 73);
        map.put("LK_W", 80);
        map.put("SD_M", 63);
        map.put("SD_W", 67);
        map.put("SR_M", 68);
        map.put("SR_W", 75);
        map.put("SJ_M", 60);
        map.put("SJ_W", 60);
        map.put("SZ_M", 84);
        map.put("SZ_W", 85);
        map.put("SE_M", 81);
        map.put("SE_W", 84);
        map.put("CH_M", 82);
        map.put("CH_W", 85);
        map.put("SY_M", 67);
        map.put("SY_W", 78);
        map.put("TW_M", 76);
        map.put("TW_W", 82);
        map.put("TJ_M", 69);
        map.put("TJ_W", 73);
        map.put("TZ_M", 63);
        map.put("TZ_W", 67);
        map.put("TH_M", 73);
        map.put("TH_W", 81);
        map.put("TG_M", 60);
        map.put("TG_W", 62);
        map.put("TK_M", 60);
        map.put("TK_W", 60);
        map.put("TO_M", 69);
        map.put("TO_W", 73);
        map.put("TT_M", 71);
        map.put("TT_W", 76);
        map.put("TN_M", 75);
        map.put("TN_W", 78);
        map.put("TR_M", 76);
        map.put("TR_W", 81);
        map.put("TM_M", 65);
        map.put("TM_W", 72);
        map.put("TC_M", 77);
        map.put("TC_W", 83);
        map.put("TV_M", 65);
        map.put("TV_W", 69);
        map.put("VI_M", 77);
        map.put("VI_W", 80);
        map.put("UG_M", 61);
        map.put("UG_W", 65);
        map.put("UA_M", 67);
        map.put("UA_W", 77);
        map.put("AE_M", 77);
        map.put("AE_W", 79);
        map.put("GB_M", 79);
        map.put("GB_W", 83);
        map.put("US_M", 76);
        map.put("US_W", 81);
        map.put("UY_M", 74);
        map.put("UY_W", 81);
        map.put("UZ_M", 69);
        map.put("UZ_W", 74);
        map.put("VU_M", 69);
        map.put("VU_W", 72);
        map.put("VA_M", 60);
        map.put("VA_W", 60);
        map.put("VE_M", 68);
        map.put("VE_W", 76);
        map.put("VN_M", 79);
        map.put("VN_W", 71);
        map.put("WF_M", 77);
        map.put("WF_W", 83);
        map.put("EH_M", 66);
        map.put("EH_W", 70);
        map.put("YE_M", 64);
        map.put("YE_W", 68);
        map.put("ZM_M", 60);
        map.put("ZM_W", 66);
        map.put("ZW_M", 60);
        map.put("ZW_W", 63);
        return map;
    }

    /** Counts how much time lived and print it */
    private static void howManyLived(Calendar dateOfBirth){
        Calendar dateNow = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(sdf.format(dateOfBirth.getTime()), formatter);
        LocalDate date2 = LocalDate.parse(sdf.format(dateNow.getTime()), formatter);

        Period period = Period.between(date1, date2);
        System.out.println("Lived: " + period.getYears() + " " + (period.getYears() == 0 ? "year" : "years") + ", " +
                period.getMonths() + " " + (period.getMonths() == 0 ? "month" : "months") +  " and " +
                period.getDays() + " " + (period.getDays() == 0 ? "day" : "days"));
    }

//    Only for RU
//    private static String correctYear(int x) {
//        if (x == 0 || (x >= 11 && x <= 20)) return "лет";   // если 0 или с 11 до 20 - лет
//        if (x%10 == 1) return "год";                        // если окончается на 1 - год
//        if ((x%10 == 2) | (x%10 == 3) | (x%10 == 4)) return "года"; // на 2, 3 или 4 - года
//        else return "лет";                                  // все остальное - лет
//    }  // Как правильно выдать - год, года или лет.
//    private static String correctDay(int x) {
//        if (x == 0 || (x >= 11 && x <= 20)) return "дней";  // если 0 - лет
//        if (x%10 == 1) return "день";                       // если окончается на 1 - год
//        if ((x%10 == 2) | (x%10 == 3) | (x%10 == 4)) return "дня"; // на 2, 3 или 4 - года
//        else return "дней";                                 // все остальное - лет
//    }   // Как правильно выдать - день, дня или дней.
//    private static String correctMonth(int x) {
//        if (x == 0) return "месяцев";
//        if (x == 1) return "месяц";
//        if (x == 2 | x == 3 | x == 4) return "месяца";
//        else return "месяцев";
//    } // Как правильно выдать - месяц, месяца или месяцев.

    /** Counts how much time a person has to live and print it */
    private static void leftToLive(Calendar dateOfBirth, boolean sex, String country, HashMap<String, Integer> map){
        Calendar dateNow = Calendar.getInstance();

        String whatToAdd = "";
        whatToAdd = whatToAdd.concat(country);
        whatToAdd = (sex) ? whatToAdd.concat("_M") : whatToAdd.concat("_W");

        dateOfBirth.add(Calendar.YEAR, map.get(whatToAdd));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);                  // format to print
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);    // format to parse

        LocalDate date1 = LocalDate.parse(sdf.format(dateNow.getTime()), formatter);    // create LocalDate now
        LocalDate date2 = LocalDate.parse(sdf.format(dateOfBirth.getTime()), formatter);// LocalDate date of death

        if (dateNow.before(dateOfBirth)) {                  // if Death_Date didn't pass
            Period period = Period.between(date1, date2);   // compare now with date of death and show how much left
            System.out.println("Left: " + period.getYears() + " " + (period.getYears() == 0 ? "year" : "years") + ", " +
                    period.getMonths() + " " + (period.getMonths() == 0 ? "month" : "months") + " " +
                    period.getDays() + " " + (period.getDays() == 0 ? "day" : "days"));
        } else {                                            // если Дата_смерти уже наступила
            Period period = Period.between(date2, date1);   // сравнить дату смерти с сейчас и вывести сколько пережил
            System.out.println("You lived over  " + period.getYears() + " " + (period.getYears() == 0 ? "year" : "years") + ", " +
                    period.getMonths() + " " + (period.getMonths() == 0 ? "month" : "months") + " and " +
                    period.getDays() + " " + (period.getDays() == 0 ? "day!" : "days!"));
        }

        System.out.printf("Average time of life for %s in %s is %d %s.%n", (sex ? "man" : "woman"), country, map.get(whatToAdd), (map.get(whatToAdd) == 0 ? "year" : "years"));
    }
}