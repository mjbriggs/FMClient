package model;
import java.io.Serializable;
import java.util.Random;

/** Person object modeling a row in the Person table.
 * Contains Person information
 * @author Michael Briggs
 * @version 1.0 Oct 12, 2018
 */
public class Person implements Serializable {
    /** unique identifier for this person */
    private String personID;
    /** user to which this person belongs */
    private String descendant;
    /** person's first name */
    private String firstName;
    /** person's last name */
    private String lastName;
    /** person's gender */
    private String gender;
    /** ID of person's father */
    private String father;
    /** ID of person's mother */
    private String mother;
    /** ID of person's spouse */
    private String spouse;

    /** Default Constructor
     */
    public Person(){
        personID = "";
        descendant = "";
        firstName = "";
        lastName = "";
        gender = "";
        father = "";
        mother = "";
        spouse = "";
    };
    /** Constructor that sets person values
     * @param personIDIn ID of person to be set
     * @param descendentIn descendent to be set
     * @param firstNameIn first name of person to be set
     * @param lastNameIn last name of person to be set
     * @param genderIn gender of person to be set
     * @param fatherIn father of person to be set
     * @param motherIn mother of person to be set
     * @param spouseIn spouse of person to be set
     */
    public Person(String personIDIn, String descendentIn, String firstNameIn, String lastNameIn,
                  String genderIn, String fatherIn, String motherIn, String spouseIn){
        personID = personIDIn;
        descendant = descendentIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        gender = genderIn;

        if(fatherIn == null)
            father = "";
        else
            father = fatherIn;

        if(motherIn == null)
            mother = "";
        else
            mother = motherIn;

        if(spouseIn == null)
            spouse = "";
        else
            spouse = spouseIn;

    }
    public String getPersonID(){
        return personID;
    };
    public void setPersonID(String personIDIn){
        personID = personIDIn;
    };
    public String getDescendent(){
        return descendant;
    };
    public void setDescendent(String descendentIn){
        descendant = descendentIn;
    };
    public String getFirstName(){
        return firstName;
    };
    public void setFirstName(String firstNameIn){
        firstName = firstNameIn;
    };
    public String getLastName(){
        return lastName;
    };
    public void setLastName(String lastNameIn){
        lastName = lastNameIn;
    };
    public String getGender(){
        return gender;
    };
    public void setGender(String genderIn){
        gender = genderIn;
    };
    public String getFather(){
        return father;
    };
    public void setFather(String fatherIn){
        father = fatherIn;
    };
    public String getMother(){
        return mother;
    };
    public void setMother(String motherIn){
        mother = motherIn;
    };
    public String getSpouse(){
        return spouse;
    };
    public void setSpouse(String spouseIn){
        spouse = spouseIn;
    };
    public boolean validPerson(){
        System.out.println("checking if Person is valid");
        if(personID == null || descendant == null|| firstName == null
                || lastName == null || gender == null){
            System.out.println("Person is NOT valid");
            return false;
        }
        else if(personID.equals("") || descendant.equals("") || firstName.equals("")
                || lastName.equals("") || gender.equals("")){
            System.out.println("Person is NOT valid");
            return false;
        }
        else if(!gender.equals("m") && !gender.equals("f")){
            System.out.println("Person is NOT valid");
            return false;
        }
        else {
            System.out.println("Person is valid");
            return true;
        }
    }
    public String generatePersonID(){
        int wordLength = 10;
        String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!_-+~";
        Random random = new Random();
        String personOut = "";
        for(int i = 0; i < wordLength; i++){
            personOut += availableChars.charAt(random.nextInt(availableChars.length()));
        }
        return personOut;
    }
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        else if(o instanceof Person){
            Person p = (Person) o;
            if(this.personID.equals(p.getPersonID()) &&
                    this.descendant.equals(p.getDescendent()) &&
                    this.firstName.equals(p.getFirstName()) &&
                    this.lastName.equals(p.getLastName()) &&
                    this.gender.equals(p.getGender()) &&
                    this.father.equals(p.getFather()) &&
                    this.mother.equals(p.getMother()) &&
                    this.spouse.equals(p.getSpouse())){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return "PersonID : " + this.personID + ", descendant : " + this.descendant + ", firstName : " + this.firstName +
                ", lastName : " + this.lastName + ", gender : " + this.gender + ", father : " + this.father + ", mother : " + this.mother
                + ", spouse : " + this.spouse + ";";
    }

}
