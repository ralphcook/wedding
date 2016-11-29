package rc.wedding.model;

public class Relationship
{
  public enum Name { FIANCE, FIANCEE, SPOUSE, HUSBAND, WIFE,
                     SON, DAUGHTER, FATHER, MOTHER, SISTER, BROTHER,
                     GRANDMOTHER, GRANDFATHER, GRANDSON, GRANDDAUGHTER,
                     GREATAUNT, AUNT, UNCLE, NIECE, NEPHEW, COUSIN,
                     FRIEND, COWORKER
                   };
  private Name   relationshipName;  public Name getRelationshipName() { return relationshipName; }  public void setRelationshipName(Name n) { relationshipName = n; }
  private String personKey;         public String getPersonKey()      { return personKey;      }    public void setPersonKey(String s) { personKey = s; }
  
  public Relationship(String key, Name name) { personKey = key; relationshipName = name; } 
}
