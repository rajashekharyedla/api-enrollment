package com.wavelabs.enrollment.resource.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.enrollment.builder.DataBuilder;
import com.wavelabs.enrollment.entity.User;
import com.wavelabs.enrollment.resource.FacebookUserResource;
import com.wavelabs.enrollment.resource.GoogleUserResource;
import com.wavelabs.enrollment.service.SocialUserService;

@RunWith(MockitoJUnitRunner.class)
public class FacebookUserResourceTest {

	@InjectMocks
	private FacebookUserResource facebookUserResource;

	@Mock
	private SocialUserService socialUserService;

	@Test
	public void testFbUser() {

		when(socialUserService.persistSocialUser(any(User.class), anyString())).thenReturn("852566565255");
		facebookUserResource.addfbUser(DataBuilder.getUser(), "852566565255");
		verify(socialUserService, times(1)).persistSocialUser(any(User.class), anyString());

	}

}
