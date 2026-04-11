<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PHE Rithika — Portfolio</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { background: #0a0b0f; color: #e5e7eb; font-family: system-ui, sans-serif; }
        nav { position: sticky; top: 0; background: rgba(10,11,15,0.93); backdrop-filter: blur(8px); z-index: 50; border-bottom: 0.5px solid rgba(255,255,255,0.05); }
        .badge-dot { width: 5px; height: 5px; border-radius: 50%; background: #8b83f7; display: inline-block; }
        .skill-main { background: rgba(139,131,247,0.07); border: 0.5px solid rgba(139,131,247,0.16); color: #b5b0f0; }
        .skill-main .sd { background: #8b83f7; }
        .skill-other { background: #111318; border: 0.5px solid rgba(255,255,255,0.05); color: #9ca3af; }
        .skill-other .sd { background: #374151; }
        .sd { width: 5px; height: 5px; border-radius: 50%; display: inline-block; flex-shrink: 0; }
        .pc:hover { border-color: rgba(139,131,247,0.18) !important; }
        .tag { font-size: 10px; padding: 2px 8px; border-radius: 20px; background: rgba(139,131,247,0.07); border: 0.5px solid rgba(139,131,247,0.16); color: #b5b0f0; }
        .btn-p { padding: 9px 18px; background: #8b83f7; color: #fff; border: none; border-radius: 8px; font-size: 12px; cursor: pointer; font-weight: 500; }
        .btn-s { padding: 9px 18px; background: transparent; color: #d1d5db; border: 0.5px solid rgba(255,255,255,0.1); border-radius: 8px; font-size: 12px; cursor: pointer; }
    </style>
</head>
<body>

{{-- Nav --}}
<nav>
    <div class="max-w-2xl mx-auto px-7 py-4 flex justify-between items-center">
        <span style="font-size:13px;color:#8b83f7;font-weight:500;">phe.rithika</span>
        <div class="flex gap-5">
            @foreach(['About','Skills','Projects','Experience','Contact'] as $link)
            <a href="#{{ strtolower($link) }}" style="font-size:12px;color:#6b7280;text-decoration:none;">{{ $link }}</a>
            @endforeach
        </div>
    </div>
</nav>

<div class="max-w-2xl mx-auto px-7">

    {{-- Hero --}}
    <section id="about" class="pt-16 pb-10">
        <div style="display:inline-flex;align-items:center;gap:6px;background:rgba(139,131,247,0.07);border:0.5px solid rgba(139,131,247,0.18);border-radius:20px;padding:5px 12px;margin-bottom:24px;">
            <div class="badge-dot"></div>
            <span style="font-size:11px;color:#8b83f7;letter-spacing:0.05em;">Available for internship</span>
        </div>

        <div class="flex justify-between items-start gap-5">
            <div class="flex-1">
                <h1 style="font-size:34px;font-weight:500;line-height:1.25;color:#f9fafb;margin-bottom:14px;">
                    Hi, I'm <span style="color:#8b83f7;">PHE Rithika</span><br>Full-Stack Developer
                </h1>
                <p style="font-size:13px;color:#9ca3af;line-height:1.75;max-width:400px;margin-bottom:28px;">
                    Year 4 IT student at ITC, building modern web apps with Laravel, Vue, and NestJS.
                    Passionate about DevOps, CI/CD, and clean architecture.
                </p>
                <div class="flex gap-3">
                    <button class="btn-p">View projects</button>
                    <button class="btn-s">Download CV</button>
                </div>
            </div>
            <div style="width:88px;height:88px;border-radius:50%;background:linear-gradient(135deg,#8b83f7,#4338ca);display:flex;align-items:center;justify-content:center;font-size:24px;font-weight:500;color:#fff;flex-shrink:0;">PR</div>
        </div>

        {{-- Stats --}}
        <div style="display:flex;margin-top:36px;border:0.5px solid rgba(255,255,255,0.05);border-radius:12px;overflow:hidden;">
            @foreach([['4+','Years studying'],['10+','Technologies'],['5+','Projects built'],['ITC','Institute']] as $s)
            <div style="flex:1;padding:16px;text-align:center;{{ !$loop->last ? 'border-right:0.5px solid rgba(255,255,255,0.05);' : '' }}">
                <div style="font-size:18px;font-weight:500;color:#f9fafb;">{{ $s[0] }}</div>
                <div style="font-size:11px;color:#6b7280;margin-top:2px;">{{ $s[1] }}</div>
            </div>
            @endforeach
        </div>
    </section>

    {{-- Skills --}}
    <section id="skills" class="mb-11">
        <div class="flex items-center gap-3 mb-5">
            <span style="font-size:11px;text-transform:uppercase;letter-spacing:0.08em;color:#6b7280;white-space:nowrap;">Skills</span>
            <div style="flex:1;height:0.5px;background:rgba(255,255,255,0.05);"></div>
        </div>
        <div style="display:grid;grid-template-columns:repeat(4,1fr);gap:6px;">
            @foreach(['Laravel','Vue.js','NestJS','Spring Boot'] as $skill)
            <div class="skill-main flex items-center gap-2" style="padding:9px 10px;border-radius:8px;font-size:11px;">
                <div class="sd"></div>{{ $skill }}
            </div>
            @endforeach
            @foreach(['JavaScript','Tailwind CSS','Docker','Jenkins','Ansible','PostgreSQL','Git','Linux'] as $skill)
            <div class="skill-other flex items-center gap-2" style="padding:9px 10px;border-radius:8px;font-size:11px;">
                <div class="sd"></div>{{ $skill }}
            </div>
            @endforeach
        </div>
    </section>

    {{-- Projects --}}
    <section id="projects" class="mb-11">
        <div class="flex items-center gap-3 mb-5">
            <span style="font-size:11px;text-transform:uppercase;letter-spacing:0.08em;color:#6b7280;white-space:nowrap;">Projects</span>
            <div style="flex:1;height:0.5px;background:rgba(255,255,255,0.05);"></div>
        </div>
        <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px;">
            @foreach([
                ['🌐','Portfolio Website','Personal portfolio deployed via Jenkins CI/CD pipeline with Ansible automation.',['Laravel','Tailwind','Jenkins','Ansible']],
                ['⚡','Fullstack App','Full-stack app with reactive frontend, RESTful API and PostgreSQL database.',['Vue','NestJS','PostgreSQL']],
                ['📱','Spring Boot API','RESTful API with JWT auth, role-based access control and Swagger docs.',['Spring Boot','Java','JWT']],
                ['🎓','Academic Projects','Algorithms, data structures and systems programming projects from studies.',['C++','Algorithms','OOP']],
            ] as $p)
            <div class="pc" style="background:#111318;border:0.5px solid rgba(255,255,255,0.05);border-radius:12px;padding:18px;cursor:pointer;transition:border-color 0.2s;">
                <div style="width:34px;height:34px;border-radius:8px;background:rgba(139,131,247,0.07);border:0.5px solid rgba(139,131,247,0.16);display:flex;align-items:center;justify-content:center;font-size:15px;margin-bottom:14px;">{{ $p[0] }}</div>
                <div style="font-size:13px;font-weight:500;color:#f9fafb;margin-bottom:5px;">{{ $p[1] }}</div>
                <div style="font-size:11px;color:#6b7280;line-height:1.6;margin-bottom:10px;">{{ $p[2] }}</div>
                <div class="flex flex-wrap gap-1">
                    @foreach($p[3] as $tag)<span class="tag">{{ $tag }}</span>@endforeach
                </div>
            </div>
            @endforeach
        </div>
    </section>

    {{-- Experience --}}
    <section id="experience" class="mb-11">
        <div class="flex items-center gap-3 mb-5">
            <span style="font-size:11px;text-transform:uppercase;letter-spacing:0.08em;color:#6b7280;white-space:nowrap;">Experience</span>
            <div style="flex:1;height:0.5px;background:rgba(255,255,255,0.05);"></div>
        </div>
        <div class="flex flex-col gap-3">
            @foreach([
                ['💼','DevOps Practitioner','Academic Lab — ITC','2026 — Present'],
                ['🔧','Full-Stack Developer','Personal Projects','2023 — Present'],
            ] as $e)
            <div style="background:#111318;border:0.5px solid rgba(255,255,255,0.05);border-radius:10px;padding:16px;display:flex;gap:14px;align-items:flex-start;">
                <div style="width:36px;height:36px;border-radius:8px;background:rgba(139,131,247,0.07);border:0.5px solid rgba(139,131,247,0.16);display:flex;align-items:center;justify-content:center;font-size:15px;flex-shrink:0;">{{ $e[0] }}</div>
                <div>
                    <div style="font-size:13px;font-weight:500;color:#f9fafb;">{{ $e[1] }}</div>
                    <div style="font-size:11px;color:#6b7280;margin-top:2px;">{{ $e[2] }}</div>
                    <div style="font-size:11px;color:#8b83f7;margin-top:4px;">{{ $e[3] }}</div>
                </div>
            </div>
            @endforeach
        </div>
    </section>

    {{-- Education --}}
    <section class="mb-11">
        <div class="flex items-center gap-3 mb-5">
            <span style="font-size:11px;text-transform:uppercase;letter-spacing:0.08em;color:#6b7280;white-space:nowrap;">Education</span>
            <div style="flex:1;height:0.5px;background:rgba(255,255,255,0.05);"></div>
        </div>
        @foreach([
            ['Bachelor of Engineering in IT','Institute of Technology of Cambodia','2022 — Present',true],
            ['DevOps & CI/CD','Jenkins · Ansible · Docker · Linux','2026',true],
            ['High School Diploma','Phnom Penh, Cambodia','2022',false],
        ] as $e)
        <div class="flex gap-4" style="{{ !$loop->last ? 'padding-bottom:20px;' : '' }}">
            <div class="flex flex-col items-center pt-1">
                <div style="width:8px;height:8px;border-radius:50%;background:{{ $e[3] ? '#8b83f7' : '#374151' }};flex-shrink:0;"></div>
                @if(!$loop->last)<div style="width:0.5px;flex:1;background:rgba(255,255,255,0.05);margin-top:5px;"></div>@endif
            </div>
            <div style="{{ !$loop->last ? 'padding-bottom:4px;' : '' }}">
                <div style="font-size:13px;font-weight:500;color:#f9fafb;">{{ $e[0] }}</div>
                <div style="font-size:11px;color:#6b7280;margin-top:2px;">{{ $e[1] }}</div>
                <div style="font-size:11px;color:#8b83f7;margin-top:4px;">{{ $e[2] }}</div>
            </div>
        </div>
        @endforeach
    </section>

    {{-- Certifications --}}
    <section class="mb-11">
        <div class="flex items-center gap-3 mb-5">
            <span style="font-size:11px;text-transform:uppercase;letter-spacing:0.08em;color:#6b7280;white-space:nowrap;">Certifications</span>
            <div style="flex:1;height:0.5px;background:rgba(255,255,255,0.05);"></div>
        </div>
        <div style="display:grid;grid-template-columns:1fr 1fr;gap:8px;">
            @foreach([
                ['🐳','Docker Fundamentals','Docker Inc.'],
                ['⚙️','CI/CD with Jenkins','ITC Lab'],
                ['🌱','Laravel Developer','Self-certified'],
                ['☁️','Linux Administration','ITC Lab'],
            ] as $c)
            <div style="background:#111318;border:0.5px solid rgba(255,255,255,0.05);border-radius:10px;padding:14px;display:flex;align-items:center;gap:12px;">
                <div style="width:32px;height:32px;border-radius:8px;background:rgba(139,131,247,0.07);border:0.5px solid rgba(139,131,247,0.16);display:flex;align-items:center;justify-content:center;font-size:14px;flex-shrink:0;">{{ $c[0] }}</div>
                <div>
                    <div style="font-size:12px;font-weight:500;color:#e5e7eb;">{{ $c[1] }}</div>
                    <div style="font-size:11px;color:#6b7280;margin-top:1px;">{{ $c[2] }}</div>
                </div>
            </div>
            @endforeach
        </div>
    </section>

    {{-- Contact --}}
    <section id="contact" class="mb-14">
        <div class="flex items-center gap-3 mb-5">
            <span style="font-size:11px;text-transform:uppercase;letter-spacing:0.08em;color:#6b7280;white-space:nowrap;">Contact</span>
            <div style="flex:1;height:0.5px;background:rgba(255,255,255,0.05);"></div>
        </div>
        <div style="background:#111318;border:0.5px solid rgba(255,255,255,0.05);border-radius:14px;padding:32px;text-align:center;">
            <h3 style="font-size:16px;font-weight:500;color:#f9fafb;margin-bottom:8px;">Let's work together</h3>
            <p style="font-size:13px;color:#6b7280;margin-bottom:24px;">Open to internship opportunities and collaborative projects.</p>
            <div class="flex justify-center flex-wrap gap-3">
                <a href="mailto:pherithika@gmail.com" style="padding:9px 18px;background:#8b83f7;color:#fff;border-radius:8px;font-size:12px;text-decoration:none;">✉ pherithika@gmail.com</a>
                <a href="#" style="padding:9px 18px;background:transparent;color:#d1d5db;border:0.5px solid rgba(255,255,255,0.1);border-radius:8px;font-size:12px;text-decoration:none;">GitHub</a>
                <a href="#" style="padding:9px 18px;background:transparent;color:#d1d5db;border:0.5px solid rgba(255,255,255,0.1);border-radius:8px;font-size:12px;text-decoration:none;">Telegram</a>
                <a href="#" style="padding:9px 18px;background:transparent;color:#d1d5db;border:0.5px solid rgba(255,255,255,0.1);border-radius:8px;font-size:12px;text-decoration:none;">LinkedIn</a>
            </div>
        </div>
    </section>

</div>

{{-- Footer --}}
<div style="border-top:0.5px solid rgba(255,255,255,0.05);padding:20px 28px;max-width:700px;margin:0 auto;display:flex;justify-content:space-between;align-items:center;">
    <span style="font-size:11px;color:#4b5563;">PHE Rithika © 2026</span>
    <span style="font-size:11px;color:#4b5563;">Built with Laravel · Deployed via Jenkins</span>
</div>

</body>
</html>