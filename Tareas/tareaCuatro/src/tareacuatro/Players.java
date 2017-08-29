/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacuatro;

/**
 *
 * @author joshuansu
 */
public class Players {

    private int Salary;
    private String Born;
    private String City;
    private String PrSt;
    private String Cntry;
    private String Nat;
    private byte Ht;
    private short Wt;
    private short DftYr;
    private byte DftRd;
    private short Ovrl;
    private char Hand;
    private String LastName;
    private String FirstName;
    private String Position;
    private String Team;
    private byte GP;

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int Salary) {
        this.Salary = Salary;
    }

    public String getBorn() {
        return Born;
    }

    public void setBorn(String Born) {
        this.Born = limitante(Born, 10);
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = limitante(City, 30);
    }

    public String getPrSt() {
        return PrSt;
    }

    public void setPrSt(String PrSt) {
        this.PrSt = limitante(PrSt, 4);
    }

    public String getCntry() {
        return Cntry;
    }

    public void setCntry(String Cntry) {
        this.Cntry = limitante(Cntry, 3);
    }

    public String getNat() {
        return Nat;
    }

    public void setNat(String Nat) {
        this.Nat = limitante(Nat, 3);
    }

    public byte getHt() {
        return Ht;
    }

    public void setHt(byte Ht) {
        this.Ht = Ht;
    }

    public short getWt() {
        return Wt;
    }

    public void setWt(short Wt) {
        this.Wt = Wt;
    }

    public short getDftYr() {
        return DftYr;
    }

    public void setDftYr(short DftYr) {
        this.DftYr = DftYr;
    }

    public byte getDftRd() {
        return DftRd;
    }

    public void setDftRd(byte DftRd) {
        this.DftRd = DftRd;
    }

    public short getOvrl() {
        return Ovrl;
    }

    public void setOvrl(short Ovrl) {
        this.Ovrl = Ovrl;
    }

    public char getHand() {
        return Hand;
    }

    public void setHand(char Hand) {
        this.Hand = Hand;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = limitante(LastName, 30);
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = limitante(FirstName, 30);
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = limitante(Position, 10);
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String Team) {
        this.Team = limitante(Team, 20);
    }

    public byte getGP() {
        return GP;
    }

    public void setGP(byte GP) {
        this.GP = GP;
    }

    private String limitante(String s, int n) {

        if (s.length() > n) {
            throw new RuntimeException("Error el dato:"+s+" es demaciado grande, su tamaño es:"+s.length()+" el limite es:"+n);
        }

        StringBuilder palabra = new StringBuilder();
        palabra.append(s);
        for (int i = s.length(); i < n; i++) {
            palabra.append(' ');
        }
        return palabra.toString();
    }

    @Override
    public String toString() {
        return "Players{" + "Salary=" + Salary + ", Born=" + Born + ", City=" + City + ", PrSt=" + PrSt + ", Cntry=" + Cntry + ", Nat=" + Nat + ", Ht=" + Ht + ", Wt=" + Wt + ", DftYr=" + DftYr + ", DftRd=" + DftRd + ", Ovrl=" + Ovrl + ", Hand=" + Hand + ", LastName=" + LastName + ", FirstName=" + FirstName + ", Position=" + Position + ", Team=" + Team + ", GP=" + GP + '}';
    }
}
