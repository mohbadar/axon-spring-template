package com.unite.axon_spring.iam.eventhadler.service;

import com.unite.axon_spring.iam.commandmodel.model.Group;
import com.unite.axon_spring.iam.commandmodel.model.Permission;
import com.unite.axon_spring.iam.commandmodel.model.Role;
import com.unite.axon_spring.iam.commandmodel.model.User;
import com.unite.axon_spring.iam.eventhadler.repository.GroupRepository;
import com.unite.axon_spring.iam.eventhadler.repository.PermissionRepository;
import com.unite.axon_spring.iam.eventhadler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupService;

	@Autowired
	private PermissionRepository permissionService;

    
	public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Entry to CustomUserService: " + username);

		return loadUserByUsername(username, getCurrentEnv(), null);
	}

	public CustomUser loadUserByUsername(String username, String currentEnv, String currentLang) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		Collection<Group> userGroups = new ArrayList<>();

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		if(currentEnv != null) {
//			System.out.println("*** ENVIRONMENT ID  ****"+currentEnv);
			Collection<Permission> userPermissions = permissionService.findAllByUserAndEnv(user.getId(), currentEnv);
			return new CustomUser(user.getUsername(), user.getPassword(),
				user.isActive(), true, true, true, getGrantedAuthorities(userPermissions), currentEnv, currentLang);
		}

		// userGroups = user.getGroups();

		CustomUser userDetails = new CustomUser(user.getUsername(), user.getPassword(),
				user.isActive(), true, true, true, getAuthorities(userGroups), currentEnv, currentLang);
		return userDetails;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Group> groups) {
		HashSet<Role> roles = new HashSet<>();
		for (Group group : groups) {
			roles.addAll(group.getRoles());
		}
		return getGrantedAuthorities(getPermissions(roles));
	}

	private List<String> getPermissions(Collection<Role> roles) {
		List<String> privileges = new ArrayList<>();
		List<Permission> collection = new ArrayList<>();
		for (Role role : roles) {
			collection.addAll(role.getPermissions());
		}
		for (Permission item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	private List<GrantedAuthority> getGrantedAuthorities(Collection<Permission> permissions) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Permission permission : permissions) {
			authorities.add(new SimpleGrantedAuthority(permission.getName()));
		}
		return authorities;
    }

    public HashSet<Role> getRoles(Collection<Group> groups) {
		HashSet<Role> roles = new HashSet<>();
		for (Group group : groups) {
			roles.addAll(group.getRoles());
        }
        return roles;
    }

	
	public String getCurrentEnv() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && (authentication.getPrincipal() instanceof CustomUser)) {
			CustomUser userDetails = (CustomUser) authentication.getPrincipal();
			return userDetails.getCurrentEnv();
		}
		return null;
	}
}