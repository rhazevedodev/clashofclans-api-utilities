package com.br.natividade.api.model;

public enum ContaEnum {

    YAHOLHA("#Q0292C2Q8", "+55 18 99624-1986", true, ""),
    MALVO("#QC282QRJV", "+55 84 8827-4675", true, ""),
    SIRMACEDO("#QV8J8YCG", "+55 81 9877-0471", true, ""),
    FELIPEEDIN("#9R8GPU2U2", "+55 41 9884-0614", true, ""),
    CLEAL("#202YPGULJ", "+55 42 8826-3645", true, ""),
    HAUSSER("#QV0LYGG8Q", "55 13 99771-9774", true, ""),
    AQUILES("#YJJCRYGUU", "+55 11 99108-6620", true, ""),
    MARCIO("#8GGGR8V2L", "+55 61 9596-2364", true, ""),
    SKULL("#YGGVQ9R2", "+55 21 98656-6485", true, ""),
    TAKEO("#G228JUG98", "secundaria", false, "#8GGGR8V2L"),
    TAKUMA("#90G8CRPY0", "secundaria", false, "#8GGGR8V2L"),
    REILUCAS("#902C0L00Q", "+55 65 9362-0403", true, ""),
    MASCAPA("#QUVLJJY29", "+55 31 9159-6322", true, ""),
    SIRMACEDO02("#YV2R8PRCC", "tipoConta14", false, "#QV8J8YCG"),
    SIRLEEN("#2QP9J20J2", "+55 42 9996-4906", true, ""),
    CARDOSOMDM("#LQ80PRYCL", "+55 33 9966-7613", true, ""),
    EDYARLEANDRADE("#2YQ98V8PJ", "+55 98 8737-7164", true, ""),
    IANSAN("#YVUQYC92R", "+55 11 95881-9013", true, ""),
    SIRMACEDO03("#YJCQQ8822", "secundaria", false, "#QV8J8YCG"),
    DIAMBA("#LC9VJYVGV", "tipoConta20", true, ""),
    LUKAS("#9Y2RLUYUJ", "secundaria", false, "#8GGGR8V2L"),
    SIRMACEDO04("#YV20VUCUU", "secundaria", false, "#QV8J8YCG"),
    ULTR4VIOLENCE("#8QJYR02GU", "tipoConta23", true, ""),
    MALVOII("#2JJ8YUUVU", "secundaria", false, "#QC282QRJV"),
    LENNYSONBR("#9CP98LGQP", "secundaria", false, "#202YPGULJ"),
    YANZADA7("#Y9VRPLCCV", "secundaria", false, "#QGG8V0YL"),
    LEO("#QU02Q8VJU", "+55 22 99833-0588", true, ""),
    LEONARDOLANA("#QPQVG222G", "+33 7 65 17 95 98", true, ""),
    AK08003("#QQ0Q20PJ8", "secundaria", false, "#QC9PGP29L"),
    VORNIC3("#2GPU990GL", "+351 962 225 421", true, ""),
    LUKAS444("#PGVQLQUPJ", "#8GGGR8V2L", false, "#902C0L00Q"),
    ITALOMARTN("#QVV9L2RVC", "secundaria", false, "#28GGULVYL"),
    AK0800("#QC9PGP29L", "+55 88 9346-9185", true, ""),
    UNICORNIO("#89CY2GY0L", "+55 11 97796-5678", true, ""),
    HAZZARD("#YURL80RRU", "tipoConta35", true, ""), //verificar quem e
    EDFEL13("#LJCVVYCP0", "secundaria", false, "#9R8GPU2U2"),
    RUSHTH12("#G00GVVR2Q", "secundaria", true, "#28GGULVYL"),
    NOEL("#LUG0Y2VLP", "secundaria", false, "#902C0L00Q"),
    EDY("#92LP20Y8", "secundaria", false, "#2YQ98V8PJ"),
    NOEL2("#QYUQPP2JC", "secundaria", false, "#902C0L00Q"),
    MIGUELJACOB("#L9P0QR2C", "secundaria", false, "#V9QGLP0C"),
    AMANDA("#QV80U0VQ9", "secundaria", false, "#902C0L00Q"),
    SKULLRAIDS009("#QV2009CVL", "secundaria", false, "#YGGVQ9R2"),
    HAUSSER2("#GPLPG8QVC", "secundaria", false, "#QV0LYGG8Q"),
    ALYSSON("#V9QGLP0C", "+43 9617-6321", true, ""),
    BASARATOUJO("#28GGULVYL", "+11 98923-6569", true, ""),
    SRYAN("#QGG8V0YL","+91 8996-2884", true, "");


    private final String tag;
    private final String contato;
    private final boolean isContaPrincipal;
    private final String tagContaPrincipal;

    ContaEnum(String tag, String contato, boolean isContaPrincipal, String tagContaPrincipal) {
        this.tag = tag;
        this.contato = contato;
        this.isContaPrincipal = isContaPrincipal;
        this.tagContaPrincipal = tagContaPrincipal;
    }

    public String getTag() {
        return tag;
    }

    public String getContato() {
        return contato;
    }

    public boolean isContaPrincipal() {
        return isContaPrincipal;
    }

    public String getTagContaPrincipal() {
        return tagContaPrincipal;
    }

    public static String getEnumNameByTag(String tag) {
        for (ContaEnum conta : ContaEnum.values()) {
            if (conta.getTag().equals(tag)) {
                return conta.name();
            }
        }
        return null;
    }
}
