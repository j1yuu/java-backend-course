package kkashin.dev;

import kkashin.dev.config.AppConfig;
import kkashin.dev.model.Profile;import kkashin.dev.model.User;
import kkashin.dev.repository.ProfileRepository;import kkashin.dev.repository.UserRepository;import kkashin.dev.util.AllDataGetter;import org.hibernate.Session;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AllDataGetter allDataGetter = context.getBean(AllDataGetter.class);

        UserRepository userRepository = context.getBean(UserRepository.class);
        ProfileRepository profileRepository = context.getBean(ProfileRepository.class);

        User user = new User("Username", "user@email", null);
        Profile profile = new Profile("My bio", user);

        userRepository.saveUser(user);
        profileRepository.saveProfile(profile);

        allDataGetter.printAllData();
    }
}
