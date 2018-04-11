package sheron.csci4100u.labs.lab6;

import android.os.Parcel;
import android.os.Parcelable;


class Contact implements Parcelable{
    private int id;
    private String firstName;

    String getPhoneNum() {
        return phoneNum;
    }

    private String lastName;
    private String phoneNum;

    Contact(int id, String firstName, String lastName, String phoneNum) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
    }

    String getContactLine() {
        return lastName + " " + firstName + " " + phoneNum;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getName() {
        return getLastName() + ", " + getFirstName();
    }


    public Contact(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNum = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phoneNum);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
