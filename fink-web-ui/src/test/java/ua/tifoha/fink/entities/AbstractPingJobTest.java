package ua.tifoha.fink.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobExecutionContext;
import ua.tifoha.fink.quartz.job.AbstractPingJob;

import java.io.IOException;
import java.net.Socket;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith (MockitoJUnitRunner.class)
public class AbstractPingJobTest {
	private AbstractPingJob sut;

	@Mock
	private Socket socketMock;

	@Mock
    private JobExecutionContext oldJob;

//	@Before
//	public void setUp() throws Exception {
//		sut = spy(new AbstractPingJob("127.0.0.1", 80, 5, 3));
//		sut.setSocketSupplier(() -> socketMock);
//	}
//
//	@Test
//	public void shouldPingAddressOneTimeWhenSuccess() throws Exception {
//        sut.execute(oldJob);
//        verify(socketMock).connect(anyObject(), anyInt());
//		verify(sut).onSuccess();
//		verify(sut, never()).onFail();
//	}
//
//	@Test
//	public void shouldTryToPingAddressAttemptTimesWhenFail() throws Exception {
//		doThrow(IOException.class).when(socketMock).connect(anyObject(), anyInt());
//        sut.execute(oldJob);
//        verify(socketMock, times(3)).connect(anyObject(), anyInt());
//		verify(sut, never()).onSuccess();
//		verify(sut, times(3)).onFail();
//	}
//
//	@Test
//	public void shouldTryToPingAddressToTheFirstSuccessAttempt() throws Exception {
//		doThrow(IOException.class)
//				.doNothing()
//				.when(socketMock).connect(anyObject(), anyInt());
//        sut.execute(oldJob);
//        verify(socketMock, times(2)).connect(anyObject(), anyInt());
//		verify(sut).onSuccess();
//		verify(sut, times(1)).onFail();
//	}
//
//	@Test
//	public void pingGoogleCom() throws Exception {
//		sut = new AbstractPingJob("remote.alfa-holding.net", 3396, 1000, 3);
//        sut.execute(oldJob);
//    }
}