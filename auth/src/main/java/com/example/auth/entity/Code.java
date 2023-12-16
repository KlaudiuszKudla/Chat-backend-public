package com.example.auth.entity;

public enum Code {
    SUCCESS("Operacja zakończona sukcesem"),
    PERMIT("Przyznano dostep"),
    USER_NOT_EXIST_WITH_NAME_OR_ACCOUNT_NOT_ACTIVATED("Podany uzytkownik o danej nazwie nie istnieje lub nie aktywował konta"),
    DATA_INCORRECT("Podane dane są nieprawidłowe"),
    TOKEN_NULL_OR_EXPIRED("Wskazany token jest pusty lub nie ważny"),
    USER_EXIST_WITH_NAME("Użytkownik o podanej nazwie juz istnieje"),
    USER_EXIST_WITH_MAIL("Użytkownik o podanmym mailu juz istnieje"),
    USER_NOT_EXIST("Użytkownik nie istnieje"),
    FRIEND_REQUEST_SENT("Wysłano zaproszenie do znajomych"),
    FRIEND_REQUEST_ACCEPTED("Zaakceptowano zaproszenie do znajomych"),
    FRIEND_BLOCKED("Użytkownik został zablokowany"),
    FRIEND_DELETED("Użytkownik został usunięty z listy przyjaciół");



    public final String label;
    private Code(String label){
        this.label = label;
    }
}
