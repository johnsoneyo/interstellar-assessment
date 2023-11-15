package za.co.discovery.interstellarassessment.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Planet {

    A("Earth"),
    B("Moon"),
    C("Jupiter"),
    D("Venus"),
    E("Mars"),
    F("Saturn"),
    G("Uranus"),
    H("Pluto"),
    I("Neptune"),
    J("Mercury"),
    K("Alpha Centauri"),
    L("Luhman 16"),
    M("Epsilon Eridani"),
    N("Groombridge 34"),
    O("Epsilon Indi"),
    P("Tau Ceti"),
    Q("Kapteyn's star"),
    R("Gliese 687"),
    S("Gliese 674"),
    T("Gliese 876#"),
    U("Gliese 832"),
    V("Fomalhaut"),
    W("Virginis"),
    X("HD 102365"),
    Y("Gliese 176"),
    Z("Gliese 436"),
    A_("Pollux"),
    B_("Gliese 86"),
    C_("HIP 57050"),
    D_("Piscium"),
    E_("GJ 1214"),
    F_("Upsilon Andromedae"),
    G_("Gamma Cephei"),
    H_("HD 176051"),
    I_("Gliese 317"),
    J_("HD 38858"),
    K_("Ursae Majoris");

    private String name;
}
