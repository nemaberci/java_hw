
function hideButton(id) {
    document.getElementById(id).setAttribute("style", "display: none");
}

function showButton(id) {
    document.getElementById(id).setAttribute("style", "display: inline-flex");
}

async function applyToExam(id) {
    let res = await fetch(
        `/api/exam/apply?id=${id}`,
        {
            method: "post"
        }
    )
    if (res.status >= 200 && res.status < 300) {
        showButton(`exam-leave-${id}`);
        hideButton(`exam-apply-${id}`);
        console.log("Applying to course:", id, res);
    }

}
async function leaveExam(id) {
    let res = await fetch(
        `/api/exam/leave?id=${id}`,
        {
            method: "post"
        }
    )
    if (res.status >= 200 && res.status < 300) {
        hideButton(`exam-leave-${id}`);
        showButton(`exam-apply-${id}`);
        console.log("Leaving exam:", id, res);
    }

}
function updateExam(id) {
    document.location.href=`/exam/update/${id}`
    console.log("updating:", id);
}
async function deleteExam(id) {

    let res = await fetch(
        `/api/exam/delete/${id}`,
        {
            method: "delete"
        }
    )
    console.log(res, res.status);
    if (res.status >= 200 && res.status < 300) {
        document.getElementById(`exam-${id}`)
            .remove()
        console.log("deleting:", id)
    }

}
function addStudent(id) {
    document.location.href=`/exam/addstudent/${id}`
}
function removeStudent(id) {
    document.location.href=`/exam/removestudent/${id}`
}
function review(id) {
    document.location.href=`/exam/review/${id}`
}


let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(function(){
        return socket
    });
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/exams', function (data) {
            let courseChange = JSON.parse(data.body);
            document.getElementById(`exam-capacity-${courseChange.examId}`)
                .innerText = courseChange.openSpaces
        });
    });
}

connect()