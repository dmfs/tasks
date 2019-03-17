/*
 * Copyright 2019 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.tasks.actions;

import org.dmfs.jems.optional.Optional;
import org.dmfs.opentaskspal.readdata.EffectiveDueDate;
import org.dmfs.opentaskspal.readdata.TaskDateTime;
import org.dmfs.opentaskspal.tasks.DueData;
import org.dmfs.opentaskspal.tasks.TimeData;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.dmfs.tasks.contract.TaskContract;


/**
 * A {@link TaskAction} which defers the due date of a task by a given {@link Duration}.
 *
 * @author Marten Gajda
 */
public final class DeferDueAction extends DelegatingTaskAction
{
    public DeferDueAction(Duration duration)
    {
        super(new UpdateAction((data) -> {
            Optional<DateTime> start = new TaskDateTime(TaskContract.Tasks.DTSTART, data);
            if (start.isPresent())
            {
                return new TimeData(start.value(), new EffectiveDueDate(data).value().addDuration(duration));
            }
            else
            {
                return new DueData(new EffectiveDueDate(data).value().addDuration(duration));
            }
        }));
    }
}
