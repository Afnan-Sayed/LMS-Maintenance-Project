package com.LMS.Learning_Management_System.service;

import com.LMS.Learning_Management_System.entity.Notifications;
import com.LMS.Learning_Management_System.entity.Users;
import com.LMS.Learning_Management_System.entity.UsersType;
import com.LMS.Learning_Management_System.repository.NotificationsRepository;
import com.LMS.Learning_Management_System.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationsRepository notificationsRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private NotificationsService notificationsService;

    private UsersType instructorType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        instructorType = new UsersType();
        instructorType.setUserTypeId(3);
    }

    @Test
    void getAllNotificationsForValidUserTest() {

        Users user = new Users(1, "instructor@example.com", "password", new Date(), instructorType);
        Notifications notification1 = new Notifications(1, user, "Message 1", new Date());
        notification1.setRead(false);
        Notifications notification2 = new Notifications(2, user, "Message 2", new Date());
        notification2.setRead(true);

        List<Notifications> notificationsList = List.of(notification1, notification2);
        when(notificationsRepository.findAll()).thenReturn(notificationsList);

        List<String> result = notificationsService.getAllNotifications(1);

        assertEquals(2, result.size());
        assertTrue(result.contains("Message 1"));
        assertTrue(result.contains("Message 2"));
        verify(notificationsRepository, times(2)).save(any(Notifications.class));
    }

    @Test
    void getAllNotificationsForInvalidUserTest() {

        Users user = new Users(1, "instructor@example.com", "password", new Date(), instructorType);
        Notifications notification = new Notifications(1, user, "Message", new Date());
        List<Notifications> notificationsList = List.of(notification);

        when(notificationsRepository.findAll()).thenReturn(notificationsList);

        List<String> result = notificationsService.getAllNotifications(999);

        assertTrue(result.isEmpty());
        verify(notificationsRepository, never()).save(any(Notifications.class));
    }

    @Test
    void getAllNotificationsForUserWithNoNotifications_Test() {
        when(notificationsRepository.findAll()).thenReturn(List.of());

        List<String> result = notificationsService.getAllNotifications(1);

        assertTrue(result.isEmpty());
        verify(notificationsRepository, never()).save(any(Notifications.class));
    }

    @Test
    void getAllUnreadNotificationsForValidUser_Test() {

        Users user = new Users(1, "instructor@example.com", "password", new Date(), instructorType);
        Notifications notification1 = new Notifications(1, user, "Message 1", new Date());
        notification1.setRead(false);
        Notifications notification2 = new Notifications(2, user, "Message 2", new Date());
        notification2.setRead(true);

        List<Notifications> notificationsList = List.of(notification1, notification2);
        when(notificationsRepository.findAll()).thenReturn(notificationsList);

        List<String> result = notificationsService.getAllUnreadNotifications(1);

        assertEquals(1, result.size());
        assertTrue(result.contains("Message 1"));
        verify(notificationsRepository, times(1)).save(notification1);
    }

    @Test
    void getAllUnreadNotificationsForInvalidUserTest() {

        Users validUser = new Users(1, "instructor@example.com", "password", new Date(), instructorType);
        Notifications notification = new Notifications(1, validUser, "Message", new Date());
        notification.setRead(false);
        List<Notifications> notificationsList = List.of(notification);

        when(notificationsRepository.findAll()).thenReturn(notificationsList);

        List<String> result = notificationsService.getAllUnreadNotifications(999);

        assertTrue(result.isEmpty());
        verify(notificationsRepository, never()).save(any(Notifications.class));
    }


    @Test
    void getAllUnreadNotificationsForUserWithNoUnreadNotifications_Test() {
        Users user = new Users(1, "user1@example.com", "password", new Date(), instructorType);
        Notifications notification1 = new Notifications(1, user, "Message", new Date());
        notification1.setRead(true);

        List<Notifications> notificationsList = List.of(notification1);

        when(notificationsRepository.findAll()).thenReturn(notificationsList);

        List<String> result = notificationsService.getAllUnreadNotifications(1);

        assertTrue(result.isEmpty());
        verify(notificationsRepository, never()).save(any(Notifications.class));
    }

    @Test
    void sendNotificationForValidUser_Test() {

        Users user = new Users(1, "instructor@example.com", "password", new Date(), instructorType);
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));

        notificationsService.sendNotification("Message", 1);

        ArgumentCaptor<Notifications> captor = ArgumentCaptor.forClass(Notifications.class);
        verify(notificationsRepository).save(captor.capture());

        Notifications savedNotification = captor.getValue();
        assertEquals("Message", savedNotification.getMessage());
        assertEquals(user, savedNotification.getUserId());
        assertFalse(savedNotification.isRead());
    }

    @Test
    void sendNotificationForInvalidUser_Test() {

        when(usersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationsService.sendNotification("Message", 1));

        verify(notificationsRepository, never()).save(any(Notifications.class));
    }
}
