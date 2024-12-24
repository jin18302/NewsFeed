package NewsFeedProject.newsfeed.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DeletedMemberService {


    private static final Set<String> deletedMembers = new HashSet<>();

    public static void addToList(String email) {
        deletedMembers.add(email);
    }

    public static boolean isEmailDeleted(String email) {
        return deletedMembers.contains(email);
    }

}
