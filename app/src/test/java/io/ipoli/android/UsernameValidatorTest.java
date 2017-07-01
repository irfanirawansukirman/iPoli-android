package io.ipoli.android;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.ipoli.android.app.persistence.OnDataChangedListener;
import io.ipoli.android.app.ui.UsernameValidator;
import io.ipoli.android.feed.persistence.FeedPersistenceService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Venelin Valkov <venelin@curiousily.com>
 * on 7/1/17.
 */
public class UsernameValidatorTest {

    private FeedPersistenceService defaultFeedPersistenceService;

    private FeedPersistenceService notUniqueFeedPersistenceService;

    private UsernameValidator.ResultListener resultListener;

    @Before
    public void setUp() {
        resultListener = mock(UsernameValidator.ResultListener.class);
        defaultFeedPersistenceService = mock(FeedPersistenceService.class);
        notUniqueFeedPersistenceService = mock(FeedPersistenceService.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                OnDataChangedListener<Boolean> dataChangedListener = invocationOnMock.getArgument(1);
                dataChangedListener.onDataChanged(true);
                return null;
            }
        }).when(defaultFeedPersistenceService).isUsernameAvailable(anyString(), any(OnDataChangedListener.class));


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                OnDataChangedListener<Boolean> dataChangedListener = invocationOnMock.getArgument(1);
                dataChangedListener.onDataChanged(false);
                return null;
            }
        }).when(notUniqueFeedPersistenceService).isUsernameAvailable(anyString(), any(OnDataChangedListener.class));
    }

    @Test
    public void validate_validUsername_callOnValid() {
        UsernameValidator.validate("iPoli", defaultFeedPersistenceService, resultListener);
        verify(resultListener).onValid();
    }

    @Test
    public void validate_emptyUsername_callOnInvalidWithEmptyError() {
        UsernameValidator.validate("", defaultFeedPersistenceService, resultListener);
        verify(resultListener).onInvalid(UsernameValidator.UsernameValidationError.EMPTY);
    }

    @Test
    public void validate_notUniqueUsername_callOnInvalidWithNotUniqueError() {
        UsernameValidator.validate("iPoli", notUniqueFeedPersistenceService, resultListener);
        verify(resultListener).onInvalid(UsernameValidator.UsernameValidationError.NOT_UNIQUE);
    }
}
