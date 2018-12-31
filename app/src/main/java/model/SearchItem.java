package model;

import java.util.List;

public class SearchItem {

    public SearchItem(Person person, Event event){
        mPerson = person;
        mEvent = event;
    }

    public Person getPerson() {
        return mPerson;
    }

    public void setPerson(Person person) {
        mPerson = person;
    }

    private Person mPerson;

    public Event getEvent() {
        return mEvent;
    }

    public void setEvent(Event event) {
        mEvent = event;
    }

    private Event mEvent;
}
