
async function deleteCourse(courseId) {
    let res = await fetch(
        `/api/course/delete/${courseId}`,
        {
            method: "delete"
        }
    )
    console.log(res, res.status);
    if (res.status >= 200 && res.status < 300) {
        document.getElementById(`course-${courseId}`)
        .remove()
        console.log("deleting:", courseId)
    }
}

async function updateCourse(courseId) {
    document.location.href=`/course/update/${courseId}`
    console.log("updating:", courseId);
}

function hideButton(id) {
    document.getElementById(id).setAttribute("style", "display: none");
}

function showButton(id) {
    document.getElementById(id).setAttribute("style", "display: inline-flex");
}

async function leaveCourse(who, courseId) {
    let res = await fetch(
        `/api/course/${who}/leave?id=${courseId}`,
        {
            method: "post"
        }
    )
    if (res.status >= 200 && res.status < 300) {
        hideButton(`course-leave-${who}-${courseId}`);
        showButton(`course-apply-${who}-${courseId}`);
        console.log("Leaving course:", courseId, res);
    }
}

async function applyToCourse(who, courseId) {
    let res = await fetch(
        `/api/course/${who}/apply?id=${courseId}`,
        {
            method: "post"
        }
    )
    if (res.status >= 200 && res.status < 300) {
        showButton(`course-leave-${who}-${courseId}`);
        hideButton(`course-apply-${who}-${courseId}`);
        console.log("Applying to course:", courseId, res);
    }
}

let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(function(){
        return socket
    });
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/courses', function (data) {
            let courseChange = JSON.parse(data.body);
            document.getElementById(`course-capacity-${courseChange.courseId}`)
                    .innerText = courseChange.openSpaces
        });
    });
}

function addStudent(courseId) {
    document.location.href=`/course/addstudent/${courseId}`
}

function removeStudent(courseId) {
    document.location.href=`/course/removestudent/${courseId}`
}

connect()