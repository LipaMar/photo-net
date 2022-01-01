package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.Follow;
import photonet.server.domain.entity.Post;
import photonet.server.domain.repository.OpinionRepository;
import photonet.server.domain.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final OpinionRepository<Follow> followRepository;
    private final UserRepository userRepository;

    public boolean isFollowedByLoggedUser(String userName) {
        return followRepository.existsByAuthorUserNameAndTargetUserName(SecurityUtils.loggedUserName(), userName);
    }

    @Transactional
    public void notifyAllObservers(Post post) {
        followRepository.findAllFollowersByUserName(SecurityUtils.loggedUserName())
                .forEach(follower -> {
                    follower.getNewPosts().add(post);
                    followRepository.save(follower);
                });
    }

    @Transactional
    public void markAsSeen(Post post) {
        followRepository.findAllFollowingByUserName(SecurityUtils.loggedUserName())
                .forEach(followed -> {
                    followed.getNewPosts().remove(post);
                    followRepository.save(followed);
                });
    }

    @Transactional
    public void follow(String userName) {
        if (isFollowedByLoggedUser(userName)) {
            return;
        }
        var target = userRepository.findByUserName(userName)
                .orElseThrow(NotFoundRestException::new);
        var author = userRepository.findByUserName(SecurityUtils.loggedUserName())
                .orElseThrow(NotFoundRestException::new);
        Follow follow = Follow.builder()
                .target(target)
                .author(author)
                .build();
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(String followedUser) {
        if (isFollowedByLoggedUser(followedUser)) {
            var follow = followRepository.findByAuthorUserNameAndTargetUserName(SecurityUtils.loggedUserName(), followedUser);
            followRepository.delete(follow);
        }
    }
}
