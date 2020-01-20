package bloodrunserver.models.dto;

public class LobbyDto {
    private int id;
    private String name;
    private String description;
    private boolean hasPassword;
    private String password;
    private UserDto userOne;
    private UserDto userTwo;
    private UserDto userThree;
    private UserDto userFour;

    // constructors

    public LobbyDto(){}

    public LobbyDto(int id)
    {
        this.id = id;
    }

    public LobbyDto(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public LobbyDto(int id, String name, String description, boolean hasPassword, String password, UserDto userOne, UserDto userTwo, UserDto userThree, UserDto userFour)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hasPassword = hasPassword;
        this.password = password;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.userThree = userThree;
        this.userFour = userFour;
    }

    // getters && setters

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean getHasPassword()
    {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword)
    {
        this.hasPassword = hasPassword;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UserDto getUserDtoOne()
    {
        return userOne;
    }

    public void setUserDtoOne(UserDto userOne)
    {
        this.userOne = userOne;
    }

    public UserDto getUserDtoTwo()
    {
        return userTwo;
    }

    public void setUserDtoTwo(UserDto userTwo)
    {
        this.userTwo = userTwo;
    }

    public UserDto getUserDtoThree()
    {
        return userThree;
    }

    public void setUserDtoThree(UserDto userThree)
    {
        this.userThree = userThree;
    }

    public UserDto getUserDtoFour()
    {
        return userFour;
    }

    public void setUserDtoFour(UserDto userFour)
    {
        this.userFour = userFour;
    }
}
