class SCRSApp {
    constructor() {
        this.currentUser = JSON.parse(localStorage.getItem('user')) || null;
        this.complaints = JSON.parse(localStorage.getItem('db_complaints')) || [];
        this.init();
    }

    init() {
        this.updateUI();
        this.setupForms();
        this.updateDepartments();
        this.renderComplaints();
    }

    // Роутинг между секциями
    showSection(id) {
        if ((id === 'new-complaint' || id === 'complaints') && !this.currentUser) {
            this.showToast("Please login first!", "error");
            id = 'auth';
        }
        document.querySelectorAll('section').forEach(s => s.classList.remove('active'));
        document.getElementById(`${id}-section`).classList.add('active');
    }

    setupForms() {
        // Login
        document.getElementById('loginForm').onsubmit = (e) => {
            e.preventDefault();
            const data = Object.fromEntries(new FormData(e.target));
            // Имитация API запроса
            this.currentUser = { ...data, role: data.username === 'admin' ? 'ADMIN' : 'STUDENT' };
            localStorage.setItem('user', JSON.stringify(this.currentUser));
            this.updateUI();
            this.showSection('home');
            this.showToast(`Welcome, ${data.username}!`);
        };

        // Complaint Submit
        document.getElementById('complaintForm').onsubmit = (e) => {
            e.preventDefault();
            const data = Object.fromEntries(new FormData(e.target));
            const newComplaint = {
                id: Date.now(),
                ...data,
                status: 'NEW',
                author: this.currentUser.username,
                votes: 0,
                createdAt: new Date().toLocaleDateString()
            };
            this.complaints.push(newComplaint);
            localStorage.setItem('db_complaints', JSON.stringify(this.complaints));
            this.showToast("Complaint submitted and routed!");
            this.showSection('complaints');
            this.renderComplaints();
            e.target.reset();
        };
    }

    updateDepartments() {
        const type = document.getElementById('complaint-type').value;
        const select = document.getElementById('targetDepartment');
        const depts = type === 'ACADEMIC'
            ? ['IT Support', 'Student Affairs', 'Registrar', 'Library']
            : ['Dormitory Head', 'Canteen Manager', 'Security', 'Maintenance'];

        select.innerHTML = depts.map(d => `<option value="${d}">${d}</option>`).join('');
    }

    renderComplaints(filterType = 'all') {
        const container = document.getElementById('complaints-list');
        const list = filterType === 'all' ? this.complaints : this.complaints.filter(c => c.type === filterType);

        container.innerHTML = list.sort((a,b) => b.votes - a.votes).map(c => `
            <div class="complaint-card">
                <div style="display:flex; justify-content:space-between">
                    <span class="badge badge-new">${c.status}</span>
                    <small>${c.createdAt}</small>
                </div>
                <h3>${c.title}</h3>
                <p>${c.description}</p>
                <div style="display:flex; align-items:center; gap:15px; margin-top:10px;">
                    <strong><i class="fas fa-building"></i> ${c.targetDepartment}</strong>
                    <button onclick="app.vote(${c.id})" class="btn" style="background:#eee">
                        ⭐ ${c.votes}
                    </button>
                </div>
            </div>
        `).join('');
    }

    vote(id) {
        const comp = this.complaints.find(c => c.id === id);
        comp.votes++;
        localStorage.setItem('db_complaints', JSON.stringify(this.complaints));
        this.renderComplaints();
    }

    updateUI() {
        const authOnly = document.querySelectorAll('.auth-only');
        const adminOnly = document.querySelectorAll('.admin-only');

        if (this.currentUser) {
            authOnly.forEach(el => el.style.display = 'inline-block');
            document.getElementById('auth-btn').style.display = 'none';
            if(this.currentUser.role === 'ADMIN') {
                adminOnly.forEach(el => el.style.display = 'inline-block');
            }
        } else {
            authOnly.forEach(el => el.style.display = 'none');
            adminOnly.forEach(el => el.style.display = 'none');
            document.getElementById('auth-btn').style.display = 'inline-block';
        }
    }

    logout() {
        localStorage.removeItem('user');
        this.currentUser = null;
        this.updateUI();
        this.showSection('home');
    }

    toggleAuthTab(type) {
        document.querySelectorAll('.auth-form').forEach(f => f.classList.remove('active'));
        document.querySelectorAll('.auth-tabs button').forEach(b => b.classList.remove('active'));
        document.getElementById(`${type}Form`).classList.add('active');
        document.getElementById(`tab-${type}`).classList.add('active');
    }

    showToast(msg) {
        const t = document.getElementById('toast');
        t.innerText = msg;
        t.style.display = 'block';
        setTimeout(() => t.style.display = 'none', 3000);
    }
}

const app = new SCRSApp();